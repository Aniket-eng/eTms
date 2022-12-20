package com.example.etms1.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name="my_preference")

class DataStoreRepository(context: Context) {


    private val datastore = context.datastore

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
    }

    suspend fun saveToDatastore(name: String){
        datastore.edit { pref->
            pref[USER_NAME_KEY] = name

        }
    }

    suspend fun clearData(){
        datastore.edit{ pref->
            pref.clear()
        }

    }

    fun readData() : Flow<String> {
        return datastore.data
            .catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                }
                else{
                    throw exception
                }
            }
            .map { pref ->
                val myName = pref[USER_NAME_KEY] ?: ""
                myName
            }

    }
}

