package os.abuyahya.carsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import os.abuyahya.carsapp.data.local.CarDatabase
import os.abuyahya.carsapp.data.repository.LocalDataSourceImpl
import os.abuyahya.carsapp.domain.repository.LocalDataSource
import os.abuyahya.carsapp.others.Constants.CAR_DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CarDatabase {
        return Room.databaseBuilder(
            context,
            CarDatabase::class.java,
            CAR_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        carDatabase: CarDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            carDatabase
        )
    }
}

