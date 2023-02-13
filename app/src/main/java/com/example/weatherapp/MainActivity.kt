package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.entities.WeatherEntity

import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.mainModule.MainViewModel
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    //Varaible Main View Model
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    //Adaptador para el Spinner
    private lateinit var aaCountries: ArrayAdapter<String>




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSpinner()
        setUpViewModel()


    }

    private fun setUpViewModel(){ //Metodo para setear el view model
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getCountrySelected().observe(this){ countrySelected ->  //Observador para saber cual es el pais seleccionado

            //Recuperar datos desde la api
            var latitude = getCountryLat(countrySelected)
            var longitude = getCountryLon(countrySelected)

            mainViewModel.viewModelScope.launch {
                mainViewModel.getWeather(latitude,longitude ,"06389e8488fb77714ed2a4f8c818afab", "en")
            }

            mainViewModel.getResult().observe(this){ weatherEntity ->
                updateUi(weatherEntity)
            }
        }
    }

    //Obtener la longitud segun el pais seleccionado en el spinner para luego pasarlo en la consulta
    private fun getCountryLon(countrySelected: String): Double{
        val lon = when(countrySelected){

            "Colombia" -> -74.08083333333333

            "Brasil" -> -56.42

            "Estados Unidos" -> -73.03

            "Argentina" -> -65.80

            "Mexico" -> -103.97

            else -> 0.00
        }

        return lon
    }

    //Obtener la latitud segun el pais seleccionado en el spinner para luego pasarlo en la consulta
    private fun getCountryLat(countrySelected: String):Double{
        val lat = when(countrySelected){
            "Colombia" -> 4.598888888888888

            "Brasil" -> -8.09

            "Estados Unidos" -> 41.19

            "Argentina" -> -34.77

            "Mexico" -> 23.08

            else -> 0.00
        }
        return lat
    }

    //Actualiza la vista cuando recibe los datos de la API
    private fun updateUi(weatherEntity: WeatherEntity){
        when(weatherEntity.weather[0].main){

            "Sunny" -> binding.imageViewWeather.setImageResource(R.drawable.sunny)

            "Clear" -> binding.imageViewWeather.setImageResource(R.drawable.sunny)

            "Clouds" -> binding.imageViewWeather.setImageResource(R.drawable.clouds)

            "Rain" -> binding.imageViewWeather.setImageResource(R.drawable.rain)

            else -> binding.imageViewWeather.setImageResource(R.drawable.defaultweather)
        }

        binding.tvMainTemp.text = "${kToC(weatherEntity.main.temp)}°c"
        binding.tvMain.text = weatherEntity.weather[0].main
        binding.tvDescription.text = weatherEntity.weather[0].description
    }



    private fun kToC(kTemp: Double): Int{ //Funcion para convertir grados Kelvin(la API regresa la temperatura en esta medida) a grados Celsius
        return (kTemp - 273.15).toInt()
    }




    //Metodos Spinner

    private fun setUpSpinner(){

        aaCountries = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item)

        aaCountries.addAll(Arrays.asList("Colombia", "Brasil" , "Estados Unidos" , "Argentina" , "Mexico"))

        binding.spinnerCountry.onItemSelectedListener = this
        binding.spinnerCountry.adapter = aaCountries



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mainViewModel.setCountrySelected(aaCountries.getItem(position)!!)
        binding.tvUbication.text = aaCountries.getItem(position)




    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}