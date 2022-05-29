package ru.barsik.weatherapp.repo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weatherlocation")
data class WeatherLocation(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id : Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("state")
    var state: String?,
    @SerializedName("country")
    var country: String,
    @SerializedName("lon")
    var lon: Double,
    @SerializedName("lat")
    var lat: Double
)
{
    override fun toString(): String {
        return if(state!=null) "$title, $state, $country"
        else "$title, $country"
    }
}
