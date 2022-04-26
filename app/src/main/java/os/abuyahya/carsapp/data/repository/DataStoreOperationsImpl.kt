package os.abuyahya.carsapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import os.abuyahya.carsapp.domain.repository.DataStoreOperations
import os.abuyahya.carsapp.others.Constants.ON_BOARDING_PREFERENCE_KEY
import os.abuyahya.carsapp.others.Constants.PREFERENCES_NAME
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context): DataStoreOperations {

    val  dataStore = context.dataStore
    private object PreferenceKey {
        val onBoardingKey = booleanPreferencesKey(name = ON_BOARDING_PREFERENCE_KEY)
    }

    override suspend fun saveOnBoardingState(complete: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.onBoardingKey] = complete
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                else
                    throw exception
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferenceKey.onBoardingKey] ?: false
                onBoardingState
            }
    }
}
