package os.abuyahya.carsapp.presintation.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import os.abuyahya.carsapp.domain.use_case.UseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    val getAllCars = useCase.getAllCarsUseCase()
}
