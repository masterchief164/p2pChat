package com.example.p2pchat.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pManager
import android.os.IBinder
import com.example.p2pchat.HomeActivity

class WifiBroadcastReceiver(
    private val manager: WifiP2pManager?,
    private val channel: WifiP2pManager.Channel,
    private val activity: HomeActivity
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                when(intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)){
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
                        // Wifi P2P is enabled
                    }
                    else -> {
                        // Wifi P2P is not enabled
                    }
                }

            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {

            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {

            }
        }
    }

    override fun peekService(myContext: Context?, service: Intent?): IBinder {
        return super.peekService(myContext, service)
    }
}