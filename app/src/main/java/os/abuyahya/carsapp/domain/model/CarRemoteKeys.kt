package os.abuyahya.carsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import os.abuyahya.carsapp.others.Constants.CAR_REMOTE_KEYs_DATABASE_TABLE

@Entity(tableName = CAR_REMOTE_KEYs_DATABASE_TABLE)
data class CarRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var prevPage: Int?,
    var nextPage: Int?,
    var lastUpdated: Long?
)
