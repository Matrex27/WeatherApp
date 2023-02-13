package com.example.weatherapp.mainModule

import com.example.weatherapp.common.entities.WeatherEntity
import com.example.weatherapp.common.utils.Constants
import com.example.weatherapp.common.utils.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class remoteDatabase {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WeatherService::class.java)

    suspend fun getWeatherForecastByCoordinates(lat: Double, lon: Double, appid: String, lang: String): WeatherEntity = withContext(Dispatchers.IO){
        service.getWeatherForecastByCoordinates(lat, lon, appid, lang)
    }

}