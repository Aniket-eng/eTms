package com.example.etms1.Database

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.etms1.Models.Pass

class PassRepository(private val passDao: BuspassDao) {

    val allPases : LiveData<List<Pass>> = passDao.getAllPases()

    suspend fun insert(pass: Pass){
        passDao.insert(pass)
    }

    suspend fun delete(pass: Pass){
        passDao.delete(pass)
    }


}