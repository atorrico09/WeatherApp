package com.distillery.interview.ui.current_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.distillery.interview.R
import com.distillery.interview.data.DependencyProvider
import com.distillery.interview.data.api.WeatherAPI
import com.distillery.interview.data.models.WeatherResponse
import com.distillery.interview.databinding.FragmentCurrentWeatherBinding

class CurrentWeatherFragment : Fragment() {

    private val weatherApi = DependencyProvider.provideService(WeatherAPI::class.java)
    private val viewModelFactory = CurrentWeatherViewModel.Factory(this, null, weatherApi)
    private val viewModel: CurrentWeatherViewModel by activityViewModels { viewModelFactory }
    private lateinit var binding: FragmentCurrentWeatherBinding

    private val currentWeatherObserver = Observer<CurrentWeatherViewModel.CurrentWeatherUiModel> {
        val uiModel = it ?: return@Observer

        if (uiModel.showLoading) {
            showLoading()
        } else {
            hideLoading()
        }

        uiModel.showError?.consume()?.let { err ->
            showError(err)
        }

        uiModel.showSuccess?.consume()?.let { weatherResponse ->
            setValues(weatherResponse)
        }
    }

    private fun setValues(weatherResponse: WeatherResponse) {
        binding.apply {
            with(weatherResponse) {
                description.text = weather.firstOrNull()?.main ?: ""
                tempMax.text = getString(R.string.max_temp_text, main.temp_max.toString())
                tempMin.text = getString(R.string.min_temp_text,main.temp_min.toString())
                temp.text = getString(R.string.temp_text,main.temp.toString())
                feelsLike.text = getString(R.string.feels_like_text,main.feels_like.toString())
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.uiState.observe(viewLifecycleOwner, currentWeatherObserver)
        viewModel.getCurrentWeather()
    }

}