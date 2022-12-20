package com.example.etms1.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.etms1.Models.Pass


@Dao
interface BuspassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pass: Pass)

    @Delete
    suspend fun delete(pass: Pass)

    @Query("Select * from pass_table order by id ASC")
    fun getAllPases() : LiveData<List<Pass>>

}