package com.example.blackice


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.math.roundToInt

class WeatherActivity : AppCompatActivity() {
    companion object{
        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "ed968019ecb61c8576672bdd54fe2a10"
        var lat = "37.445293"
        var lon = "126.785823"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        //Create Retrofit Builder
        val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, AppId)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("WeatherActivity", "result :" + t.message)
            }

            override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
            ) {
                if(response.code() == 200){
                    val weatherResponse = response.body()
                    Log.d("WeatherActivity", "result: " + weatherResponse.toString())
                    var cTemp =  weatherResponse!!.main!!.temp - 273.15  //켈빈을 섭씨로 변환
                    var minTemp = weatherResponse!!.main!!.temp_min - 273.15
                    var maxTemp = weatherResponse!!.main!!.temp_max - 273.15
                    val stringBuilder =
                            "현재기온: " + cTemp.roundToInt() + "\n" +
                                    "습도 : " + weatherResponse!!.main!!.humidity+ "\n"

                    weather.text = stringBuilder
                }
            }

        })
    }
}

interface WeatherService{

    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("appid") appid: String) :
            Call<WeatherResponse>
}

class WeatherResponse(){
    @SerializedName("weather") var weather = ArrayList<Weather>()
    @SerializedName("main") var main: Main? = null
    @SerializedName("wind") var wind : Wind? = null
    @SerializedName("sys") var sys: Sys? = null
}

class Weather {
    @SerializedName("id") var id: Int = 0
    @SerializedName("main") var main : String? = null
    @SerializedName("description") var description: String? = null
    @SerializedName("icon") var icon : String? = null
}

class Main {
    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat()
    @SerializedName("temp_min")
    var temp_min: Float = 0.toFloat()
    @SerializedName("temp_max")
    var temp_max: Float = 0.toFloat()

}

class Wind {
    @SerializedName("speed")
    var speed: Float = 0.toFloat()
    @SerializedName("deg")
    var deg: Float = 0.toFloat()
}

class Sys {
    @SerializedName("country")
    var country: String? = null
    /*
    @SerializedName("sunrise")
    var sunrise: Long = 0
    @SerializedName("sunset")
    var sunset: Long = 0 */
}