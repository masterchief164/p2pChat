package com.example.p2pchat.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log.d

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            val serviceIntent = Intent(context, TestService::class.java)
            context?.startService(serviceIntent)
        } else if(intent?.action == "stopService"){
            d("broadcast ", "stop Service")
            val serviceIntent = Intent(context, TestService::class.java)
            context?.stopService(serviceIntent)
        }
    }
}