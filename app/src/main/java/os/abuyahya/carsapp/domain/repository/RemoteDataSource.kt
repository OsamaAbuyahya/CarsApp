package os.abuyahya.carsapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import os.abuyahya.carsapp.domain.model.Car

interface RemoteDataSource {
    fun getAllCars(): Flow<PagingData<Car>>
    fun searchCars(query: String): Flow<PagingData<Car>>
}
