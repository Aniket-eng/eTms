package com.example.etms1.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val datastore = DataStoreRepository(application)

    val read = datastore.readData().asLiveData(Dispatchers.IO)

    fun clearData() {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.clearData()
        }
    }

    fun savaData(user_name : String){
        viewModelScope.launch {
            datastore.saveToDatastore(user_name)
        }
    }
}