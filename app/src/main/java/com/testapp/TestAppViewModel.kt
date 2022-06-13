package com.testapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapp.model.SerializationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TestAppViewModel @Inject constructor(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    private val _serializationState = mutableStateOf<SerializationModel>(SerializationModel.Init)
    val serializationState get() = _serializationState

    fun getTestString() {
        viewModelScope.launch {
            repository.getTestString().collect {
                Timber.d("Test : DataStore Test $it")
                _serializationState.value = it
            }
        }
    }

    fun updateTestString() {
        viewModelScope.launch {
            repository.updateTestString(serializationModel = SerializationModel.TestSerialization)
        }
    }
    fun updateTestString2() {
        viewModelScope.launch {
            repository.updateTestString(serializationModel = SerializationModel.TestSerializationSecond)
        }
    }
    fun updateTestString3() {
        viewModelScope.launch {
            repository.updateTestString2(serializationModel = SerializationModel.TestSerializationSecond)
        }
    }
}
