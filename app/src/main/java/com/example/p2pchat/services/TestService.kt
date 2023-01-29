package com.example.p2pchat.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log.e
import com.example.p2pchat.R
import kotlinx.coroutines.*

class TestService : Service() {
    var job: Job? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = CoroutineScope(Dispatchers.IO).launch {
//            while (true) {
//                e("Service", "service is running")
//                delay(1000)
//            }
        }

        val channel = NotificationChannel(
            "p2pchat",
            "P2P Chat",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "used to sync messages"
        val stopServiceIntent = Intent(applicationContext, MyBroadcastReceiver::class.java)
        stopServiceIntent.action = "stopService"
        val stopService = PendingIntent.getBroadcast(applicationContext, 1, stopServiceIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(this, "p2pchat")
            .setContentTitle("P2P Chat")
            .setContentText("P2P Chat is running in background")
            .setSmallIcon(R.drawable.chaticon)
            .addAction(R.drawable.chaticon, "Stop", stopService)
            .build()
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        startForeground(1, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        job?.cancel()
        job = null
        super.onDestroy()
    }
}