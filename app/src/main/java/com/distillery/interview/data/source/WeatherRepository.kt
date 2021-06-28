package com.distillery.interview.data.source

import com.distillery.interview.data.models.CurrentWeatherResponse
import com.distillery.interview.data.models.DailyWeatherResponse
import com.distillery.interview.data.models.HourlyWeatherResponse
import com.distillery.interview.data.models.Result
import com.distillery.interview.data.source.remote.WeatherRemoteDataSource

class WeatherRepository {

    private val weatherRemoteDataSource: WeatherDataSource

    init {
        weatherRemoteDataSource = WeatherRemoteDataSource
    }

    suspend fun getCurrentWeather(): Result<CurrentWeatherResponse> =
        weatherRemoteDataSource.getCurrentWeather()

    suspend fun getHourlyWeather(): Result<HourlyWeatherResponse> =
        weatherRemoteDataSource.getHourlyWeather()

    suspend fun getDailyWeather(): Result<DailyWeatherResponse> =
        weatherRemoteDataSource.getDailyWeather()

}