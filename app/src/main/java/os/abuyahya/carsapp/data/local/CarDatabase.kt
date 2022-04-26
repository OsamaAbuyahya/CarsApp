package os.abuyahya.carsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import os.abuyahya.carsapp.data.local.dao.CarDao
import os.abuyahya.carsapp.data.local.dao.CarRemoteKeysDao
import os.abuyahya.carsapp.domain.model.Car
import os.abuyahya.carsapp.domain.model.CarRemoteKeys
import os.abuyahya.carsapp.others.Constants.CAR_DATABASE

@Database(entities = [Car::class, CarRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class CarDatabase: RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): CarDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, CarDatabase::class.java)
            } else {
                Room.databaseBuilder(context, CarDatabase::class.java, CAR_DATABASE)
            }

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun carDao(): CarDao
    abstract fun carRemoteKeysDao(): CarRemoteKeysDao
}
