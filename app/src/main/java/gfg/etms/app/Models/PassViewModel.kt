package gfg.etms.app.Models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gfg.etms.app.Database.PassDatabase
import gfg.etms.app.Database.PassRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class PassViewModel(application: Application) : AndroidViewModel(application) {



    private val repository : PassRepository
    val allpass : LiveData<List<Pass>>
    private val _data = MutableLiveData<String>()
    //val data: LiveData<String> get() = _data

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

    fun resetAutoKey() = viewModelScope.launch(Dispatchers.IO) {
        repository.resetID()
    }
    fun setData(item: String) {
        Log.d("data", item)
        _data.postValue(item)
    }

    fun getData() : LiveData<String> {
        Log.d("data",_data.value.toString())
        return _data
    }

    fun getrecentdata() : LiveData<Pass>{

        val recentpass : LiveData<Pass> = repository.load(repository.maxId())

        return recentpass

    }





}