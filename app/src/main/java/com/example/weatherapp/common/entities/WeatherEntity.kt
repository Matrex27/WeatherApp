package com.example.weatherapp.common.entities

import com.example.weatherapp.common.entities.Main
import com.example.weatherapp.common.entities.Weather

class WeatherEntity(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)