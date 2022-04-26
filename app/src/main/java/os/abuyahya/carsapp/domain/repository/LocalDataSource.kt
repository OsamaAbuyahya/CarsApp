package os.abuyahya.carsapp.domain.repository

import os.abuyahya.carsapp.domain.model.Car

interface LocalDataSource {

    suspend fun getSelectedCar(carId: Int): Car
}
