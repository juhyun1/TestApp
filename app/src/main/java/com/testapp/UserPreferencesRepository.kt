/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.testapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.testapp.model.SerializationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun updateTestString(serializationModel: SerializationModel) {
        val string = encodeToString(value = serializationModel)
        dataStore.edit {
            it[PreferencesKeys.Serialization] = string
        }
    }
    suspend fun updateTestString2(serializationModel: SerializationModel) {
        val string = encodeToString(value = serializationModel)
        dataStore.edit {
            it[PreferencesKeys.SerializationSecond] = string
        }
    }

    suspend fun getTestString(): Flow<SerializationModel> = dataStore.data.map {
            val str = it[PreferencesKeys.Serialization]
            val obj: SerializationModel = str?.decodeFromString() ?: SerializationModel.Init
            Timber.d("Test : in datastore $obj")
            obj
        }
}

object PreferencesKeys {
    val Serialization = stringPreferencesKey("serialization")
    val SerializationSecond = stringPreferencesKey("SerializationSecond")
}

inline fun <reified T> encodeToString(value: T): String {
    return Json.encodeToString(value)
}
inline fun <reified T> String.decodeFromString(): T {
    return Json.decodeFromString(this)
}