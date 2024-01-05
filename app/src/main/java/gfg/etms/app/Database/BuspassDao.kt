package gfg.etms.app.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import gfg.etms.app.Models.Pass


@Dao
interface BuspassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pass: Pass)

    @Delete
    suspend fun delete(pass: Pass)

    @Query("Select * from pass_table order by id DESC")
    fun getAllPases() : LiveData<List<Pass>>

    @Query("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = 'pass_table'")
    fun resetId()

    @Query("SELECT MAX(id) from pass_table")
    fun maxId(): LiveData<Int>

    @Query("SELECT * from pass_table WHERE id = :pos LIMIT 1")
    fun userLoad(pos: LiveData<Int>): LiveData<Pass>

}