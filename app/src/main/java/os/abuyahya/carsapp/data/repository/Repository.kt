package os.abuyahya.carsapp.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.repository.DataStoreOperations
import os.abuyahya.carsapp.domain.repository.LocalDataSource
import os.abuyahya.carsapp.domain.repository.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {
    fun getAllCars(): Flow<PagingData<Car>> {
        return remote.getAllCars()
    }

    suspend fun getSelectedCar(carId: Int): Car {
        return localDataSource.getSelectedCar(carId = carId)
    }

    fun searchCars(query: String): Flow<PagingData<Car>> {
        return remote.searchCars(query = query)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }
}
