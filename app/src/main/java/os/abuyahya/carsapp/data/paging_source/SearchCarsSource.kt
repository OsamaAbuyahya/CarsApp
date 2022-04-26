package os.abuyahya.carsapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import os.abuyahya.carsapp.data.remote.CarApi
import os.abuyahya.carsapp.domain.model.Car
import javax.inject.Inject

class SearchCarsSource(
    private val carApi: CarApi,
    private val query: String
): PagingSource<Int, Car>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Car> {
        return try {
            val apiResponse = carApi.searchCars(name = query)
            val cars = apiResponse.cars
            if (cars.isNotEmpty()) {
                LoadResult.Page(
                    cars,
                    apiResponse.prevPage,
                    apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    emptyList(),
                    null,
                    null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Car>): Int? {
        return state.anchorPosition
    }

}
