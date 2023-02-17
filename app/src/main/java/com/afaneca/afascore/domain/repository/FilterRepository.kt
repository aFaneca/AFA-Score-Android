package com.afaneca.afascore.domain.repository

import com.afaneca.afascore.domain.model.FilterData
import kotlinx.coroutines.flow.Flow

/**
 * Created by António Faneca on 2/16/2023.
 */
interface FilterRepository {
    suspend fun getFilterData(): FilterData?
    suspend fun saveFilterData(data: FilterData): Boolean
}