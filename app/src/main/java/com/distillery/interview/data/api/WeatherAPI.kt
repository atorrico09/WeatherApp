package com.distillery.interview.data.api

import com.distillery.interview.BuildConfig
import com.distillery.interview.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET

interface WeatherAPI {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
    }

    @GET("data/2.5/weather?q=Buenos Aires&appid=$WEATHER_API_KEY")
    suspend fun getCurrentWeather(): Response<WeatherResponse>
}