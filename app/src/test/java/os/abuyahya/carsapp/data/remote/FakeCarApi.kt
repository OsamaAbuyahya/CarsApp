package os.abuyahya.carsapp.data.remote

import androidx.compose.ui.text.toLowerCase
import os.abuyahya.carsapp.domain.model.ApiResponse
import os.abuyahya.carsapp.domain.model.Car

class FakeCarApi : CarApi {

    private val cars = listOf(
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

    override suspend fun getAllCars(page: Int): ApiResponse {
        return ApiResponse(
            success = false
        )
    }

    override suspend fun searchCars(name: String): ApiResponse {
        val searchedCars = findCars(name = name)
        return ApiResponse(
            success = true,
            message = "ok",
            cars = searchedCars
        )
    }

    private fun findCars(name: String): List<Car> {
        val founded = mutableListOf<Car>()
        return if (name.isNotEmpty()) {
            founded.addAll(
                cars.filter { car ->
                    car.name.lowercase().contains(name.lowercase())
                }
            )
            founded
        } else {
            emptyList()
        }
    }
}
