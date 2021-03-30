package com.qbo.apptvqbodatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginMaganer(content: Context)  {

    // Create the dataStore and give it a name same as shared preferences
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    private val mDataStore: DataStore<Preferences> = content.dataStore

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_PASSWORD_KEY = stringPreferencesKey("USER_PASSWORD")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys

    suspend fun storeUser(usuario: String, password: String) {
        mDataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = usuario
            preferences[USER_PASSWORD_KEY] = password
            // here it refers to the preferences we are editing
        }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine

    val userNameFlow: Flow<String> = mDataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    // Create a name flow to retrieve name from the preferences
    val userAgeFlow: Flow<String> = mDataStore.data.map {
        it[USER_PASSWORD_KEY] ?: ""
    }

}