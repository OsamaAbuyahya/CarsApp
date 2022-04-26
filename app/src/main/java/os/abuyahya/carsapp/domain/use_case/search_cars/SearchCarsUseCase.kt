package os.abuyahya.carsapp.domain.use_case.search_cars

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import os.abuyahya.carsapp.data.repository.Repository
import os.abuyahya.carsapp.domain.model.Car

class SearchCarsUseCase(
    private val repository: Repository
) {

    operator fun invoke(query: String): Flow<PagingData<Car>> {
        return repository.searchCars(query = query)
    }
}
