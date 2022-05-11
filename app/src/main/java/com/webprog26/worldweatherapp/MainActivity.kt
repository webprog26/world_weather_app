package com.webprog26.worldweatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.webprog26.worldweatherapp.databinding.ActivityMainBinding
import com.webprog26.worldweatherapp.db.WeatherDatabase
import com.webprog26.worldweatherapp.view_model.WeatherDataViewModel
import com.webprog26.worldweatherapp.location.LocationProvider
import com.webprog26.worldweatherapp.network.createWeatherApi
import com.webprog26.worldweatherapp.ui.MainFragment
import com.webprog26.worldweatherapp.ui.MainPresenter
import com.webprog26.worldweatherapp.weather_data.WeatherRepositoryImpl

class MainActivity : AppCompatActivity(), MainFragment.OnWeatherDataUpdateRequestedListener {

    private var forceNetworkUpdate: Boolean = false

    private lateinit var binding: ActivityMainBinding

    private lateinit var weatherDataViewModel: WeatherDataViewModel

    private val mainFragment = MainFragment.getInstance()
    private val mainPresenter = MainPresenter(mainFragment)

    companion object {
        private const val ACCESS_COARSE_LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.mainFragmentContainer.id, mainFragment).commit()
        }

        weatherDataViewModel = WeatherDataViewModel(WeatherRepositoryImpl(createWeatherApi(),
            WeatherDatabase.getInstance(WorldWeatherApplication.appContext).weatherDao()))
        weatherDataViewModel.weatherData.observe(this) { weatherData ->
            mainPresenter.onWeatherDataAvailable(weatherData)
            mainPresenter.onLoadingCompleted()
        }
        weatherDataViewModel.cityData.observe(this) { city ->
            mainPresenter.onCurrentCityAvailable(city)
        }

        locationProvider = LocationProvider(this)
        locationProvider.lastKnownLocationData.observe(this) { latLng ->
            mainPresenter.onLoadingStarted()
            weatherDataViewModel.updateWeatherData(
                latLng,
                getString(R.string.weather_api_key),
                forceNetworkUpdate
            )
            forceNetworkUpdate = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            locationProvider.getUserLocation()
        }
    }

    override fun onWeatherDataUpdateRequested() {
        if (isLocationPermissionGranted()) {
            forceNetworkUpdate = true
            locationProvider.getUserLocation()
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private fun requestLocationPermission() {
        if (shouldRequestPermissionRationale()) {
            showLocationPermissionExplanationDialog()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                ACCESS_COARSE_LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun showLocationPermissionExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.settings_dialog_title)
            setMessage(R.string.settings_dialog_message)
                .setPositiveButton(R.string.settings_dialog_positive_button_text) { _, _ ->
                    startAppSettings(context)
                }
                .setNegativeButton(R.string.settings_dialog_negative_button_text) { dialog, _ ->
                    dialog.dismiss()
                }.run {
                    create()
                        .show()
                }
        }
    }

    private fun startAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    private fun shouldRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size == 1 &&
            grantResults[0] == PackageManager.PERMISSION_DENIED && shouldRequestPermissionRationale()
        ) {
            showLocationPermissionExplanationDialog()
        }
    }
}