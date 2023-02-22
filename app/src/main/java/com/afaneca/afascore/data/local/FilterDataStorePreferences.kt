package com.afaneca.afascore.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by António Faneca on 2/16/2023.
 */
@Singleton
class FilterDataStorePreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "filter_data")

    val filterData: Flow<String?>
        get() = context.dataStore.data.map { prefs ->
            prefs[FILTER_DATA]
        }

    suspend fun saveFilterData(data: String) {
        context.dataStore.edit { prefs -> prefs[FILTER_DATA] = data }
    }

    suspend fun deleteFilterData() {
        context.dataStore.edit { prefs -> prefs.remove(FILTER_DATA) }
    }

    companion object {
        private val FILTER_DATA = stringPreferencesKey("filter_data_obj")
    }
}