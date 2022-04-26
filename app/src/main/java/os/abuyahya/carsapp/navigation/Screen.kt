package os.abuyahya.carsapp.navigation

sealed class Screen(val route: String) {

    object Splash: Screen("splash_screen")
    object Welcome: Screen("welcome_screen")
    object Home: Screen("home_screen")
    object Details: Screen("details_screen/{carId}") {
        fun passCarId(carId: Int): String {
            return "details_screen/$carId"
        }
    }
    object Search: Screen("search_screen")
}
