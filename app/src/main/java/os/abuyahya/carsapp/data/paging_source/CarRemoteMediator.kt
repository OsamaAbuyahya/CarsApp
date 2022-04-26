package os.abuyahya.carsapp.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import os.abuyahya.carsapp.data.local.CarDatabase
import os.abuyahya.carsapp.data.remote.CarApi
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.model.CarRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class CarRemoteMediator(
    private val carApi: CarApi,
    private val carDatabase: CarDatabase
): RemoteMediator<Int, Car>() {

    private val carDao = carDatabase.carDao()
    private val carRemoteKeyDao = carDatabase.carRemoteKeysDao()


    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = carRemoteKeyDao.getRemoteKeys(carId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            Log.d("RemoteMediator", "Up to date!")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("RemoteMediator", "Refresh")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Car>): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1)?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(remoteKeys != null)
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(remoteKeys != null)
                    nextPage
                }
            }
            val response = carApi.getAllCars(page = page)
            if (response.cars.isNotEmpty()) {
                carDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        carDao.deleteAllCars()
                        carRemoteKeyDao.deleteAllRemoteKeys()
                    }

                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val lastUpdate = response.lastUpdated
                    val keys = response.cars.map { car ->
                        CarRemoteKeys(
                            id = car.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = lastUpdate
                        )
                    }
                    carRemoteKeyDao.addAllRemoteKeys(carRemoteKeys = keys)
                    carDao.addCars(cars = response.cars)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Car>
    ): CarRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                carRemoteKeyDao.getRemoteKeys(carId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Car>
    ): CarRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { car ->
                carRemoteKeyDao.getRemoteKeys(carId = car.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Car>
    ): CarRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { car ->
                carRemoteKeyDao.getRemoteKeys(carId = car.id)
            }
    }
}
