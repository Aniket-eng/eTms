package gfg.etms.app.Database

import android.util.Log
import androidx.lifecycle.LiveData
import gfg.etms.app.Models.Pass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PassRepository(private val passDao: BuspassDao) {

    val allPases : LiveData<List<Pass>> = passDao.getAllPases()
    val recentpass : LiveData<Pass> = passDao.userLoad()


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

    fun load() : LiveData<Pass>{
            return passDao.userLoad()
    }

    fun maxId() : Int {
        return passDao.maxId()
    }


}