package gfg.etms.app.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gfg.etms.app.Models.Pass
import gfg.etms.app.Utilities.DATABASE_NAME

@Database(entities = [Pass::class], version = 3, exportSchema = false)
abstract class PassDatabase : RoomDatabase() {

    abstract fun getPassDao() : BuspassDao


    companion object{

        @Volatile
        private var INSTANCE : PassDatabase? = null

        fun getDatabase(context : Context) : PassDatabase {

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