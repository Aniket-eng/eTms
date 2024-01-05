package gfg.etms.app.Models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "pass_table")
data class Pass(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "name") val name : String?,
    @ColumnInfo(name = "emp_id") val emp_id : String?,
    @ColumnInfo(name = "from")val from : String?,
    @ColumnInfo(name = "to")val to : String?,
    @ColumnInfo(name = "Pickup")val Pickup : String?,
    @ColumnInfo(name = "Drop")val Drop : String?,
    @ColumnInfo(name = "bus_stop")val bus_stop : String?,
    @ColumnInfo(name = "route")val route : String?,
    @ColumnInfo(name = "start_date")val start_date : String?,
    @ColumnInfo(name = "end_date")val end_date : String?

) : java.io.Serializable
