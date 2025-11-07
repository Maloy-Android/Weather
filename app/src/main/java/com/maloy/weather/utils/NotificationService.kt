package com.maloy.weather.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.maloy.weather.R
import com.maloy.weather.data.WeatherResponse

class NotificationService(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "current_weather_channel"
        const val NOTIFICATION_ID = 1
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Текущая погода",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Уведомления о текущей погоде"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendCurrentWeather(weather: WeatherResponse) {
        if (!PermissionUtils.hasNotificationPermission(context)) {
            return
        }
        val emoji = getWeatherEmoji(weather.current.condition, weatherResponse = null)
        val title = "$emoji Погода в ${weather.location.name}"

        val message = """
            ${weather.current.temperature.toInt()}°C, ${weather.current.condition}
            Ощущается как: ${weather.current.feelsLike.toInt()}°C
            Ветер: ${weather.current.windSpeed.toInt()} м/с
            ${weather.location.country}
        """.trimIndent()

        sendNotification(title, message)
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(false)
            .setOngoing(true)
            .setShowWhen(false)
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun cancelNotification() {
        with(NotificationManagerCompat.from(context)) {
            cancel(NOTIFICATION_ID)
        }
    }
}