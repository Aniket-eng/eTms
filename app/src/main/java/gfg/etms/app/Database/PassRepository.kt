package gfg.etms.app.Database

import android.util.Log
import androidx.lifecycle.LiveData
import gfg.etms.app.Models.Pass

class PassRepository(private val passDao: BuspassDao) {

    val allPases : LiveData<List<Pass>> = passDao.getAllPases()


    suspend fun insert(pass: Pass){
        passDao.insert(pass)
    }

    suspend fun delete(pass: Pass){
        passDao.delete(pass)
    }

    fun resetID(){
        Log.d("dara","reset krrr e re")
        passDao.resetId()
    }

    fun load(pos: LiveData<Int>) : LiveData<Pass>{
      return passDao.userLoad(pos)
    }

    fun maxId() : LiveData<Int>{
        return passDao.maxId()
    }


}