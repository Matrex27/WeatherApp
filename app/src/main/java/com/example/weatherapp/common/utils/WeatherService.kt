package com.example.weatherapp.common.utils

import com.example.weatherapp.common.entities.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(Constants.ONE_CALL_PATH)
    suspend fun getWeatherForecastByCoordinates(
        @Query(Constants.LAT_PARAM) lat: Double,
        @Query(Constants.LON_PARAM) lon: Double,
        @Query(Constants.APPID_PARAM)appid: String,
        @Query(Constants.LANG_PARAM)lang: String
    ): WeatherEntity

}