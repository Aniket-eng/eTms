package com.example.etms1.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.etms1.Models.Pass
import com.example.etms1.Utilities.DATABASE_NAME

@Database(entities = [Pass::class], version = 2, exportSchema = false)
abstract class PassDatabase : RoomDatabase() {

    abstract fun getPassDao() : BuspassDao


    companion object{

        @Volatile
        private var INSTANCE : PassDatabase? = null

        fun getDatabase(context : Context) : PassDatabase{

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PassDatabase::class.java ,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                instance

            }
        }


    }
}