package com.afaneca.afascore.ui.matchList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaneca.afascore.common.Resource
import com.afaneca.afascore.domain.model.mapToUi
import com.afaneca.afascore.domain.useCase.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by António Faneca on 2/13/2023.
 */
@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MatchListState())
    val state = _state.asStateFlow()

    init {
        getMatchList()
    }

    private fun getMatchList() {
        getMatchesUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value =
                        _state.value.copy(
                            isLoading = false,
                            matchList = (it.data?.matchList?.map { item -> item.mapToUi() }),
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

    fun onRefreshClicked() {
        getMatchList()
    }

    fun onFilterClicked() {
        _state.value = _state.value.copy(isFiltering = true)
    }

    fun onFilterDismiss() {
        _state.value = _state.value.copy(isFiltering = false)
    }
}