package com.afaneca.afascore.ui.matchList

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.mapToUi
import com.afaneca.afascore.domain.useCase.*
import com.afaneca.afascore.ui.model.MatchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val saveFiltersUseCase: SaveFiltersUseCase,
    private val getReconciledFiltersUseCase: GetReconciledFiltersUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MatchListState())
    val state = _state.asStateFlow()

    private var getMatchListHandler: Handler? = null
    private val getMatchListRunnable = object : Runnable {
        override fun run() {
            // refresh match list every 30 seconds
            getMatchList()
            getMatchListHandler?.postDelayed(this, 30_000)
        }
    }

    init {
        getMatchListHandler = Handler(Looper.getMainLooper())
        getMatchListHandler?.post(getMatchListRunnable)
    }

    override fun onCleared() {
        super.onCleared()
        getMatchListHandler?.removeCallbacks(getMatchListRunnable)
    }

    private fun getMatchList() {
        viewModelScope.launch {
            getMatchesUseCase().onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                isSneakyLoading = false,
                                matchList = (it.data?.matchList?.map { item -> item.mapToUi() }),
                                filteredMatchList = (it.data?.filteredMatchList?.map { item -> item.mapToUi() }),
                                filterData = it.data?.filterData?.mapToUi()
                            )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isSneakyLoading = false,
                            error = it.message
                        )
                    }

                    is Resource.Loading -> {
                        if (_state.value.matchList.isNullOrEmpty())
                            _state.value = _state.value.copy(isLoading = true)
                        else
                            _state.value = _state.value.copy(isSneakyLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onRefreshClicked() {
        getMatchList()
    }

    fun onFilterClicked() {
        // if list is null or empty, do nothing
        viewModelScope.launch {
            _state.value.matchList?.let { matchList ->
                if (matchList.isEmpty() && _state.value.filterData?.teamsList?.isEmpty() != false) return@launch
                val updatedFilterData =
                    getReconciledFiltersUseCase(matchList.map { Match.mapFromUi(it) })
                _state.value =
                    _state.value.copy(isFiltering = true, filterData = updatedFilterData.mapToUi())
            }
        }
    }

    fun onFilterDismiss() {
        _state.value = _state.value.copy(isFiltering = false)
    }

    fun onFilterApplyClicked(
        teams: List<FilterableTeamUiModel>,
        competitions: List<FilterableCompetitionUiModel>,
        statuses: List<FilterableStatusUiModel>
    ) {
        onFilterDismiss()
        viewModelScope.launch {
            saveFiltersUseCase(FilterDataUiModel(teams, competitions, statuses))
            getMatchList()
        }
    }

    fun resetFilters() {
        viewModelScope.launch {
            saveFiltersUseCase.resetFilters()
            getMatchList()
        }
    }

    fun toggleFavorite(match: MatchUiModel) {
        if (match.isFavorite) {
            removeFavorite(match.id)
        } else {
            addFavorite(match.id)
        }
    }

    private fun addFavorite(matchId: String) {
        viewModelScope.launch {
            addFavoriteUseCase(matchId)
            getMatchList()
        }
    }

    private fun removeFavorite(matchId: String) {
        viewModelScope.launch {
            removeFavoriteUseCase(matchId)
            getMatchList()
        }
    }
}