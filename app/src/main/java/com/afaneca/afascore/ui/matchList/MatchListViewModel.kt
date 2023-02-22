package com.afaneca.afascore.ui.matchList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.Match
import com.afaneca.afascore.domain.model.mapToUi
import com.afaneca.afascore.domain.useCase.GetReconciledFiltersUseCase
import com.afaneca.afascore.domain.useCase.GetMatchesUseCase
import com.afaneca.afascore.domain.useCase.SaveFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val saveFiltersUseCase: SaveFiltersUseCase,
    private val getReconciledFiltersUseCase: GetReconciledFiltersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MatchListState())
    val state = _state.asStateFlow()

    init {
        getMatchList()
    }

    private fun getMatchList() {
        viewModelScope.launch {
            getMatchesUseCase().onEach {
                when (it) {
                    is Resource.Success -> {
                        _state.value =
                            _state.value.copy(
                                isLoading = false,
                                matchList = (it.data?.matchList?.map { item -> item.mapToUi() }),
                                filteredMatchList = (it.data?.filteredMatchList?.map { item -> item.mapToUi() }),
                                filterData = it.data?.filterData?.mapToUi()
                            )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = it.message)
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
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
}