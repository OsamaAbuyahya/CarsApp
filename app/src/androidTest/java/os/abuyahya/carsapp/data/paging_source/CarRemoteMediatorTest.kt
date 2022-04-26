package os.abuyahya.carsapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.*
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import os.abuyahya.carsapp.data.local.CarDatabase
import os.abuyahya.carsapp.data.remote.FakeCarApi2
import os.abuyahya.carsapp.domain.model.Car

@ExperimentalCoroutinesApi
class CarRemoteMediatorTest {

    private lateinit var carApi: FakeCarApi2
    private lateinit var carDatabase: CarDatabase

    @Before
    fun setup() {
        carApi = FakeCarApi2()
        carDatabase = CarDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsSuccessResult() =
        runBlocking {
            val remoteMediator = CarRemoteMediator(
                carApi = carApi,
                carDatabase = carDatabase
            )
            val pagingState = PagingState<Int, Car>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Success)
        }


    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsSuccessResultAndEndOfPaginationTrueWhenNoMoreData() =
        runBlocking {
            carApi.clearData()
            val remoteMediator = CarRemoteMediator(
                carApi = carApi,
                carDatabase = carDatabase
            )
            val pagingState = PagingState<Int, Car>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Success)
            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() =
        runBlocking {
            carApi.addException()
            val remoteMediator = CarRemoteMediator(
                carApi = carApi,
                carDatabase = carDatabase
            )
            val pagingState = PagingState<Int, Car>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 3),
                leadingPlaceholderCount = 0
            )

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is MediatorResult.Error)
        }




    @After
    fun cleanup() {
        carDatabase.clearAllTables()
    }

}
