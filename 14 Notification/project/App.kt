package com.fernando.basicnotification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_ID_01,
                "Canal 1",
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = DESCRIPTION_CHANNEL1 }

            val channel2 = NotificationChannel(
                CHANNEL_ID_02,
                "Canal 2",
                NotificationManager.IMPORTANCE_LOW).apply {
                    description = DESCRIPTION_CHANNEL2
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannels(listOf(channel1, channel2))
        }
    }
    companion object{
        const val CHANNEL_ID_01 = "channel1"
        const val CHANNEL_ID_02 = "channel2"
        const val CHANNEL_FERNANDO = "fernando"
        const val DESCRIPTION_CHANNEL1 = "Descrição do canal do Fernando 1"
        const val DESCRIPTION_CHANNEL2 = "Descrição do canal do Fernando 2"
    }
}