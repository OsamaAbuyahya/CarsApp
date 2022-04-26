package os.abuyahya.carsapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import os.abuyahya.carsapp.data.repository.DataStoreOperationsImpl
import os.abuyahya.carsapp.data.repository.Repository
import os.abuyahya.carsapp.domain.repository.DataStoreOperations
import os.abuyahya.carsapp.domain.use_case.UseCase
import os.abuyahya.carsapp.domain.use_case.get_all_cars.GetAllCarsUseCase
import os.abuyahya.carsapp.domain.use_case.get_selected_car.GetSelectedCarUseCase
import os.abuyahya.carsapp.domain.use_case.read_onboarding.ReadOnBoardingUseCase
import os.abuyahya.carsapp.domain.use_case.save_onboarding.SaveOnBoardingUseCase
import os.abuyahya.carsapp.domain.use_case.search_cars.SearchCarsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: Repository): UseCase {
        return UseCase(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository),
            getAllCarsUseCase = GetAllCarsUseCase(repository = repository),
            searchCarsUseCase = SearchCarsUseCase(repository = repository),
            getSelectedCarUseCase = GetSelectedCarUseCase(repository = repository)
        )
    }
}
