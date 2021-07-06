package com.distillery.interview.ui.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.distillery.interview.data.models.Result
import com.distillery.interview.data.source.WeatherRepository

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    fun getCurrentWeather() = liveData {
        emit(Result.Loading())

        try {
            emit(weatherRepository.getCurrentWeather())
        } catch (e: Exception) {
            emit(Result.Error(e))
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

