package com.example.ecommapp

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    val context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {

        dataStore = applicationContext.createDataStore(
            name = "app_preferences"
        )
    }

    val first_time: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[FIRST_TIME] ?: true
        }

    suspend fun trigger_first_time(first_time: Boolean) {
        dataStore.edit { preferences ->
            preferences[FIRST_TIME] = first_time
        }
    }

    companion object {
        val FIRST_TIME = preferencesKey<Boolean>("first_time")
    }

}