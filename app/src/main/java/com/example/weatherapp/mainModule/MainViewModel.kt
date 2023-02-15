package com.example.weatherapp.mainModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.entities.WeatherEntity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel : ViewModel() {

    private var repository = MainInteractor()
    private var result = MutableLiveData<WeatherEntity>()
    private var countrySelected = MutableLiveData<String>()

    fun getResult(): LiveData<WeatherEntity> {
        return result
    }

    fun getCountrySelected(): LiveData<String>{
        return countrySelected
    }

    fun setCountrySelected(country: String){
        countrySelected.value = country
    }


    suspend fun getWeather(lat: Double, lon: Double, appid: String, lang: String){

        viewModelScope.launch {
            try {
                var resultServer = repository.getWeatherForecast(lat, lon, appid, lang)
                result.value = resultServer

            }catch (e: HttpException){
                (e as? HttpException)?.let {
                    when(it.code()){
                        400 -> Log.i("Error:", "Error 400 Bad Request")

                        else -> Log.i("Error:", "Error desconocido")
                    }

                }

            }
        }

    }


}