package com.maloy.weather.utils.app

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

object LocationUtils {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onSuccess: (lat: Double, lon: Double) -> Unit,
        onError: (message: String) -> Unit
    ) {
        try {
            if (fusedLocationClient == null) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            }

            fusedLocationClient?.lastLocation
                ?.addOnSuccessListener { location ->
                    if (location != null) {
                        onSuccess(location.latitude, location.longitude)
                    } else {
                        onError("Не удалось получить местоположение")
                    }
                }
                ?.addOnFailureListener { exception ->
                    onError("Ошибка получения местоположения")
                }
        } catch (_: Exception) {
            onError("Ошибка доступа к местоположению")
        }
    }

    fun getCityNameFromLocation(context: Context, lat: Double, lon: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses?.firstOrNull()?.let { address ->
                address.locality ?:
                address.subAdminArea ?:
                address.adminArea ?:
                "Неизвестный город"
            } ?: "Неизвестный город"

        } catch (_: Exception) {
            "Неизвестный город"
        }
    }
}