package com.example.studo.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

fun getChannelId(name: String): String = "${StudoApplication.ApplicationContext.packageName}-$name"
const val CHANNEL_NEWJOB = "New_job_channel"
const val COMMENTS_NEWAPPLY = "New_apply_channel"
@RequiresApi(api = Build.VERSION_CODES.O)
fun createNotificationChannel(name: String, description: String, importance: Int): NotificationChannel {
    val channel = NotificationChannel(getChannelId(name), name, importance)
    channel.description = description
    channel.setShowBadge(true)
    return channel
}
@RequiresApi(api = Build.VERSION_CODES.O)
fun createNotificationChannels() {
    val channels = mutableListOf<NotificationChannel>()
    channels.add(createNotificationChannel(
        CHANNEL_NEWJOB,
        "New job is added",
        NotificationManagerCompat.IMPORTANCE_HIGH
    ))
    channels.add(createNotificationChannel(
        COMMENTS_NEWAPPLY,
        "Someone applied to your job",
        NotificationManagerCompat.IMPORTANCE_HIGH
    ))
    val notificationManager = StudoApplication.ApplicationContext.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannels(channels)
}