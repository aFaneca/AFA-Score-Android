package com.afaneca.afascore.common

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.afaneca.afascore.R
import com.afaneca.afascore.domain.useCase.GetFavoritesUseCase
import com.afaneca.afascore.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class PushNotificationService: FirebaseMessagingService() {

    @Inject lateinit var getFavoritesUseCase: GetFavoritesUseCase

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.d("onMessageReceived: ${message.data}")

        runBlocking {
            val list = getFavoritesUseCase().first()
            if (shouldShowNotification(message.data, list)){
                Timber.d("Message will be shown, since it contains a match in the user's favorites")
                showNotification(message)
            } else {
                Timber.d("Message will NOT be shown, since it DOES NOT contain a match in the user's favorites")
            }

        }
    }

    private fun shouldShowNotification(
        data: Map<String, String>,
        favoriteList: List<String>
    ): Boolean {
        return favoriteList.contains(data.getOrDefault("matchId", ""));
    }

    /**
     * When app receives notification and is in the foreground
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("FCM TOKEN: $token")
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createNotificationChannel())
        }

        notificationManager.notify(Random.nextInt(), notification)
    }

    @TargetApi(26)
    private fun createNotificationChannel(): NotificationChannel {
        val ctx: Context = this
        val name: CharSequence = ctx.getString(R.string.notification_channel_name)
        val description = ctx.getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description
        return channel
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "afascore-notif-id"
    }
}