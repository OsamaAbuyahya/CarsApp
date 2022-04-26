package os.abuyahya.carsapp.di

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import os.abuyahya.carsapp.data.local.CarDatabase
import os.abuyahya.carsapp.data.remote.CarApi
import os.abuyahya.carsapp.data.repository.RemoteDataSourceImpl
import os.abuyahya.carsapp.domain.repository.RemoteDataSource
import os.abuyahya.carsapp.others.Constants.BASE_URL
import os.abuyahya.carsapp.others.Constants.CONNECT_TIMEOUT
import os.abuyahya.carsapp.others.Constants.READ_TIMEOUT
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideCarsApi(retrofit: Retrofit): CarApi {
        return retrofit.create(CarApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        carApi: CarApi,
        carDatabase: CarDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            carApi = carApi,
            carDatabase = carDatabase
        )
    }

}
