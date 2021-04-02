package com.example.notificationapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "com.example.notificationapp"
    private val description = "Test Notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btn_notify.setOnClickListener {

            val intent = Intent(this,LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            val contentView = RemoteViews(packageName,R.layout.notification_layout)
            contentView.setTextViewText(R.id.notif_title, "Test App")
            contentView.setTextViewText(R.id.notif_content, "This is my first notification")

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
            } else {
                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }
    }
}