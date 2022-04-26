package os.abuyahya.carsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import os.abuyahya.carsapp.data.local.CarDatabase
import os.abuyahya.carsapp.data.paging_source.CarRemoteMediator
import os.abuyahya.carsapp.data.paging_source.SearchCarsSource
import os.abuyahya.carsapp.data.remote.CarApi
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.repository.RemoteDataSource
import os.abuyahya.carsapp.others.Constants.ITEMS_PER_PAGE

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val carApi: CarApi,
    private val carDatabase: CarDatabase
): RemoteDataSource {

    val carDao = carDatabase.carDao()

    override fun getAllCars(): Flow<PagingData<Car>> {
        val pagingSourceFactory = { carDao.getAllCars() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CarRemoteMediator(
                carApi = carApi,
                carDatabase = carDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchCars(query: String): Flow<PagingData<Car>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchCarsSource(carApi = carApi, query = query)
            }
        ).flow
    }
}
