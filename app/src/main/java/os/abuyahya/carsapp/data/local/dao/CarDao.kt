package os.abuyahya.carsapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import os.abuyahya.carsapp.domain.model.Car

@Dao
interface CarDao {

    @Query("SELECT * FROM car_table ORDER BY id ASC")
    fun getAllCars(): PagingSource<Int, Car>

    @Query("SELECT * FROM car_table WHERE id =:carId")
    fun getSelectedCar(carId: Int): Car

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCars(cars: List<Car>)

    @Query("DELETE FROM car_table")
    suspend fun deleteAllCars()
}
