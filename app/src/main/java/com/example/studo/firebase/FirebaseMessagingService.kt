package com.example.studo.firebase

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.studo.R
import com.example.studo.data.api.ConverterFactory.gson
import com.example.studo.data.model.Job
import com.example.studo.helpers.CHANNEL_NEWJOB
import com.example.studo.helpers.StudoApplication
import com.example.studo.helpers.getChannelId
import com.example.studo.ui.main.view.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data!= null) {
            val title = remoteMessage.data!!["title"].toString()
            val body  = remoteMessage.data!!["body"].toString()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("job", remoteMessage.data!!["job"].toString())
            intent.putExtra("type", remoteMessage.data!!["type"].toString())

            val pendingIntent = PendingIntent.getActivity(
                StudoApplication.ApplicationContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            val notification = NotificationCompat.Builder(StudoApplication.ApplicationContext, getChannelId(CHANNEL_NEWJOB))
                .setSmallIcon(R.drawable.ic_studo_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            NotificationManagerCompat.from(StudoApplication.ApplicationContext)
                .notify(1001, notification)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d("FIREBASE", "Message Notification Body: " + remoteMessage.notification.toString());
        }
    }
}