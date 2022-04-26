package os.abuyahya.carsapp.presintation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.use_case.UseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedHeroes = MutableStateFlow<PagingData<Car>>(PagingData.empty())
    val searchedHeroes = _searchedHeroes

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun searchCars(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.searchCarsUseCase(query).cachedIn(viewModelScope).collect {
                _searchedHeroes.value = it
            }
        }
    }

}
