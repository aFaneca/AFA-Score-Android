package com.afaneca.afascore.data.repository

import com.afaneca.afascore.data.local.FilterDataStorePreferences
import com.afaneca.afascore.data.local.entity.FilterDataEntity
import com.afaneca.afascore.data.local.entity.mapToDomain
import com.afaneca.afascore.domain.model.FilterData
import com.afaneca.afascore.domain.repository.FilterRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by António Faneca on 2/16/2023.
 */
class LiveFilterRepository @Inject constructor(
    private val filterDataStore: FilterDataStorePreferences
) : FilterRepository {

    override suspend fun getFilterData(): FilterData? {
        val filterData = filterDataStore.filterData.firstOrNull() ?: return null
        return try {
            val parsedFilterData = Gson().fromJson(filterData, FilterDataEntity::class.java)
            parsedFilterData.mapToDomain()
        } catch (e: Exception) {
            Timber.d(e)
            null
        }
    }

    /**
     * Returns true if save was successful
     */
    override suspend fun saveFilterData(data: FilterData): Boolean {
        val filterDataStr = Gson().toJson(data?.let { FilterDataEntity.mapFromDomain(it) })
        return try {
            filterDataStore.saveFilterData(filterDataStr)
            true
        } catch (e: Exception) {
            Timber.d(e)
            false
        }
    }

    override suspend fun resetFilterData(): Boolean {
        return try {
            filterDataStore.deleteFilterData()
            true
        } catch (e: Exception) {
            Timber.d(e)
            false
        }
    }
}