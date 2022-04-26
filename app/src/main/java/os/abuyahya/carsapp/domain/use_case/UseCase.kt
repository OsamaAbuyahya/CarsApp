package os.abuyahya.carsapp.domain.use_case

import os.abuyahya.carsapp.domain.use_case.get_all_cars.GetAllCarsUseCase
import os.abuyahya.carsapp.domain.use_case.get_selected_car.GetSelectedCarUseCase
import os.abuyahya.carsapp.domain.use_case.read_onboarding.ReadOnBoardingUseCase
import os.abuyahya.carsapp.domain.use_case.save_onboarding.SaveOnBoardingUseCase
import os.abuyahya.carsapp.domain.use_case.search_cars.SearchCarsUseCase

data class UseCase(
    var saveOnBoardingUseCase: SaveOnBoardingUseCase,
    var readOnBoardingUseCase: ReadOnBoardingUseCase,
    var getAllCarsUseCase: GetAllCarsUseCase,
    var searchCarsUseCase: SearchCarsUseCase,
    var getSelectedCarUseCase: GetSelectedCarUseCase
)
