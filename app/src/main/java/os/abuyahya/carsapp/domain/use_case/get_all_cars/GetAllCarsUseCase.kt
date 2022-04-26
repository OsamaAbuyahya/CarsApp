package os.abuyahya.carsapp.domain.use_case.get_all_cars

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import os.abuyahya.carsapp.data.repository.Repository
import os.abuyahya.carsapp.domain.model.Car

class GetAllCarsUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Car>> {
        return repository.getAllCars()
    }
}
