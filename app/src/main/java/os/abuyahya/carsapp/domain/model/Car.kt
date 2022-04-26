package os.abuyahya.carsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import os.abuyahya.carsapp.others.Constants.CAR_DATABASE_TABLE

@Serializable
@Entity(tableName = CAR_DATABASE_TABLE)
data class Car(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String,
    var image: String,
    var price: Int,
    var color: String,
    var about: String,
    var features: List<String>,
    var rating: Double,
)
