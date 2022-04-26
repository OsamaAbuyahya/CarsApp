package os.abuyahya.carsapp.presintation.screens.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.use_case.UseCase
import os.abuyahya.carsapp.others.Constants.DETAILS_ARGUMENTS_KEY
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: UseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _selectedCar: MutableStateFlow<Car?> = MutableStateFlow(null)
    val selectedCar: StateFlow<Car?> = _selectedCar

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val carId = savedStateHandle.get<Int>(DETAILS_ARGUMENTS_KEY)
            _selectedCar.value = carId?.let { useCase.getSelectedCarUseCase(carId = carId) }
            Log.d("DetailsViewModel", "Car Name: ${_selectedCar.value?.name}")
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }

    sealed class UiEvent {
        object GenerateColorPalette: UiEvent()
    }
}

