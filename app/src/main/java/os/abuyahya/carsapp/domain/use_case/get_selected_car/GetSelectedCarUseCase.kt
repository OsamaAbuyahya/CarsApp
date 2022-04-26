package os.abuyahya.carsapp.domain.use_case.get_selected_car

import os.abuyahya.carsapp.data.repository.Repository
import os.abuyahya.carsapp.domain.model.Car

class GetSelectedCarUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(carId: Int): Car {
        return repository.getSelectedCar(carId = carId)
    }
}
