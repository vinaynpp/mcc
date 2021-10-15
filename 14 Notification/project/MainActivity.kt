package com.fernando.basicnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fernando.basicnotification.databinding.ActivityMainBinding
import java.nio.channels.Channels

class MainActivity : AppCompatActivity() {
    private lateinit var bindViews: ActivityMainBinding
    private lateinit var notificationManager: NotificationManagerCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindViews.root)
        notificationManager = NotificationManagerCompat.from(this)

        bindViews.btnChannel1.setOnClickListener {
            val title = bindViews.edtTitle.text.toString()
            val message = bindViews.edtMessage.text.toString()

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_01)
                .setSmallIcon(R.drawable.ic_baseline_ring_volume_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()
            notificationManager.apply {
                notify(1, notification)
            }
        }

        bindViews.btnChannel2.setOnClickListener {
            val title = bindViews.edtTitle.text.toString()
            val message = bindViews.edtMessage.text.toString()

            val notification = NotificationCompat.Builder(this, App.CHANNEL_ID_02)
                .setSmallIcon(R.drawable.ic_baseline_ring_volume_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManager.apply {
                notify(2, notification)
            }

        }
    }
}