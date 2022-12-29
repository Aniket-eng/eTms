package com.example.etms1.Models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.etms1.Adapter.PassAdapter
import com.example.etms1.Database.PassDatabase
import com.example.etms1.Database.PassRepository
import com.example.etms1.databinding.BuspassRequestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class PassViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : PassRepository

    val allpass : LiveData<List<Pass>>

    init {
        val dao = PassDatabase.getDatabase(application).getPassDao()
        repository = PassRepository(dao)
        allpass = repository.allPases
    }

    fun deletePass(pass: Pass) = viewModelScope.launch(Dispatchers.IO) {

        repository.delete(pass)
    }

    fun insertPass(pass: Pass) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(pass)
    }
}