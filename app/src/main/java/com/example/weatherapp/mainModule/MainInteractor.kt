package com.example.weatherapp.mainModule

import com.example.weatherapp.common.entities.WeatherEntity

class MainInteractor {

    private val remoteDatabase = remoteDatabase()

    suspend fun getWeatherForecast(lat: Double, lon: Double, appid: String, lang: String): WeatherEntity =
        remoteDatabase.getWeatherForecastByCoordinates(lat, lon, appid, lang)


}