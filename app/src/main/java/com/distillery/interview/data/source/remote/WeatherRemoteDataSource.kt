package com.distillery.interview.data.source.remote

import com.distillery.interview.data.DependencyProvider
import com.distillery.interview.data.api.WeatherAPI
import com.distillery.interview.data.models.CurrentWeatherResponse
import com.distillery.interview.data.models.DailyWeatherResponse
import com.distillery.interview.data.models.HourlyWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSource : WeatherDataSource {
    private val weatherApi = DependencyProvider.provideService(WeatherAPI::class.java)

    override suspend fun getCurrentWeather(): CurrentWeatherResponse =
        withContext(Dispatchers.IO) {
            return@withContext weatherApi.getCurrentWeather()
        }

    override suspend fun getHourlyWeather(): HourlyWeatherResponse =
        withContext(Dispatchers.IO) {
            return@withContext weatherApi.getHourlyWeather()
        }

    override suspend fun getDailyWeather(): DailyWeatherResponse =
        withContext(Dispatchers.IO) {
            return@withContext weatherApi.getDailyWeather()
        }
}