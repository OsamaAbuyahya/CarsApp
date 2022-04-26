package os.abuyahya.carsapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingSource.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import os.abuyahya.carsapp.data.remote.CarApi
import os.abuyahya.carsapp.data.remote.FakeCarApi
import os.abuyahya.carsapp.domain.model.Car
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchCarsSourceTest {

    private lateinit var carApi: CarApi
    private lateinit var cars: List<Car>

    @Before
    fun setup() {
        carApi = FakeCarApi()
        cars = listOf(
            Car(
                1, "Honda Pilot", "/images/honda_pilot.jpg", 38805, "Black",
                "Honda's three-row SUV is within top 25 thanks to its strong numbers that were up 16 percent over last year.",
                listOf("Fuel Economy", "Interior", "Comfort", "Cargo", "Driver-Assistance"), 4.3
            ),
            Car(
                2, "Ford Escape", "/images/ford_escape.jpg", 27755, "Red",
                "Now that its more stylish and exciting Bronco Sport sibling is on sale, it's no wonder that the somewhat anonymous Escape has fallen in the ranks.",
                listOf("Range", "Charging", "Battery Life"), 4.2
            ),
            Car(
                3, "Hyundai Tucson", "/images/hyundai_tucson.jpg", 26195, "Blue",
                "The new 2022 version of Hyundai's compact SUV has a bold look, and it seems customers are loving it.",
                listOf("Range", "Charging", "Battery Life"), 4.4
            )
        )
    }

    @Test
    fun `Search api with existing car name, expect single car result, assert LoadResult_Page`() =
        runBlockingTest {
            val searchCarsSource = SearchCarsSource(carApi = carApi, query = "Honda")
            assertEquals<LoadResult<Int, Car>>(
                expected = LoadResult.Page(
                    data = listOf(cars[0]),
                    prevKey = null,
                    nextKey = null
                ),
                actual = searchCarsSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with existing car name, expect multiple cars result, assert LoadResult_Page`() =
        runBlockingTest {
            val searchCarsSource = SearchCarsSource(carApi = carApi, query = "H")
            assertEquals<LoadResult<Int, Car>>(
                expected = LoadResult.Page(
                    data = listOf(cars[0], cars[2]),
                    prevKey = null,
                    nextKey = null
                ),
                actual = searchCarsSource.load(
                    LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with empty car name, assert empty cars list and LoadResult_Page`() =
        runBlockingTest {
            val searchCarsSource = SearchCarsSource(carApi = carApi, query = "")
            val loadResult =  searchCarsSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )
            val result = carApi.searchCars("").cars

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

    @Test
    fun `Search api with non_existing car name, assert empty cars list and LoadResult_Page`() =
        runBlockingTest {
            val searchCarsSource = SearchCarsSource(carApi = carApi, query = "bmw")
            val loadResult =  searchCarsSource.load(
                LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )
            val result = carApi.searchCars("bmw").cars

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is LoadResult.Page }
        }

}
