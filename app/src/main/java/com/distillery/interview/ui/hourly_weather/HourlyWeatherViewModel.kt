package com.distillery.interview.ui.hourly_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.distillery.interview.data.models.Result
import com.distillery.interview.data.source.WeatherRepository

class HourlyWeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    fun getHourlyWeather(lat: Double?, lon: Double?) = liveData {
        emit(Result.Loading())

        runCatching {
            weatherRepository.getHourlyWeather(lat, lon)
        }.onSuccess {
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Error(it))
        }
    }

    class Factory(
        private val weatherRepository: WeatherRepository,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(
            modelClass: Class<T>,
        ): T {
            return modelClass.getConstructor(WeatherRepository::class.java)
                .newInstance(weatherRepository)
        }
    }
}

