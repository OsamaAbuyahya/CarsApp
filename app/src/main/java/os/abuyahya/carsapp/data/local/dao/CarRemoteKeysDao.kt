package os.abuyahya.carsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import os.abuyahya.carsapp.domain.model.CarRemoteKeys

@Dao
interface CarRemoteKeysDao {

    @Query("SELECT * FROM car_remote_keys_table WHERE id =:carId")
    suspend fun getRemoteKeys(carId: Int): CarRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(carRemoteKeys: List<CarRemoteKeys>)

    @Query("DELETE FROM car_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}
