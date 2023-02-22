package com.afaneca.afascore.domain.useCase

import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.repository.FilterRepository
import com.afaneca.afascore.ui.matchList.FilterDataUiModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by António Faneca on 2/16/2023.
 */
class SaveFiltersUseCase @Inject constructor(
    private val filtersRepository: FilterRepository
) {
    suspend operator fun invoke(filterData: FilterDataUiModel): Boolean {
        return filtersRepository.saveFilterData(FilterData.mapFromDomain(filterData))
    }

    suspend fun resetFilters() : Boolean {
        return filtersRepository.resetFilterData()
    }
}