package os.abuyahya.carsapp.data.remote

import os.abuyahya.carsapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApi {

    @GET("api/v1/cars")
    suspend fun getAllCars(
        @Query("page") page: Int
    ): ApiResponse

    @GET("api/v1/search")
    suspend fun searchCars(
        @Query("name") name: String
    ): ApiResponse
}
