package os.abuyahya.carsapp.data.repository

import androidx.room.RoomDatabase
import os.abuyahya.carsapp.data.local.CarDatabase
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    private val carDatabase: CarDatabase
): LocalDataSource {

    private val carDao = carDatabase.carDao()

    override suspend fun getSelectedCar(carId: Int): Car {
        return carDao.getSelectedCar(carId = carId)
    }
}
