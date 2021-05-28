package com.distillery.interview.ui.today_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.distillery.interview.R
import com.distillery.interview.data.CoroutinesDispatcherProvider
import com.distillery.interview.data.DependencyProvider
import com.distillery.interview.data.WeatherRepository
import com.distillery.interview.data.models.HourlyWeatherResponse
import com.distillery.interview.data.models.Result
import com.distillery.interview.databinding.FragmentTodayWeatherBinding
import com.distillery.interview.util.toDateTime
import java.text.SimpleDateFormat
import java.util.*

class TodayWeatherFragment : Fragment() {

    private val weatherRepository =
        DependencyProvider.provideRepository<WeatherRepository>()
    private val coroutinesDispatcherProvider =
        DependencyProvider.provideCoroutinesDispatcherProvider<CoroutinesDispatcherProvider>()
    private val viewModelFactory =
        TodayWeatherViewModel.Factory(this, weatherRepository, coroutinesDispatcherProvider)
    private val viewModel: TodayWeatherViewModel by activityViewModels { viewModelFactory }
    private lateinit var binding: FragmentTodayWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.uiState.observe(viewLifecycleOwner, { weatherResponse ->
            when (weatherResponse) {
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    hideLoading()
                    setValues(weatherResponse.data)
                }
                is Result.Error -> {
                    hideLoading()
                    showError(weatherResponse.errors.first())
                }
            }
        })
        viewModel.getTodayHourlyWeather()
    }

    private fun setValues(hourlyWeatherResponse: HourlyWeatherResponse) {
        binding.apply {
            with(hourlyWeatherResponse) {
                dateToday.text = hourly[0].dt.toDateTime()
            }
        }
    }

    private fun showError(err: String) {
        //TODO: Add custom error showing
        Toast.makeText(requireContext(), err, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        //TODO: Add custom loading
        Toast.makeText(requireContext(), "startLoading", Toast.LENGTH_SHORT).show()
    }

    private fun hideLoading() {
        //TODO: Add custom loading
        Toast.makeText(requireContext(), "endLoading", Toast.LENGTH_SHORT).show()
    }
}