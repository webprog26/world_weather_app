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

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ACCESS_COARSE_LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
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
        if (requestCode == ACCESS_COARSE_LOCATION_PERMISSION_REQUEST_CODE
            && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
        } else if (grantResults.size == 1 &&
            grantResults[0] == PackageManager.PERMISSION_DENIED && shouldRequestPermissionRationale()
        ) {
            showLocationPermissionExplanationDialog()
        }
    }
}