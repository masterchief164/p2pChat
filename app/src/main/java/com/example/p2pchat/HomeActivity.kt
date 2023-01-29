package com.example.p2pchat

import android.app.ActivityManager
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p2pchat.composables.AppTabBar
import com.example.p2pchat.composables.AppTabs
import com.example.p2pchat.data.User
import com.example.p2pchat.services.TestService
import com.example.p2pchat.services.WifiBroadcastReceiver
import com.example.p2pchat.ui.theme.P2pChatTheme
import com.example.p2pchat.ui.theme.firstColor
import com.example.p2pchat.ui.theme.fourthColor
import com.example.p2pchat.ui.views.ChatsView

enum class HomeTab{
    ALL_CHATS, UNREAD, PERSONAL
}


class HomeActivity : ComponentActivity() {
    private val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE){
        getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager?
    }

    var channel: WifiP2pManager.Channel? = null
    var receiver: WifiBroadcastReceiver? = null
    private val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2pChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeView()
                }
            }
        }
        if(!isServiceRunning()){
            val intent = Intent(this, TestService::class.java)
            startForegroundService(intent)
        }
        channel = manager?.initialize(this, mainLooper, null)
        channel?.also { channel ->
            receiver = WifiBroadcastReceiver(manager, channel, this)
        }

    }

    override fun onResume() {
        super.onResume()
        receiver?.also { receiver ->
            registerReceiver(receiver, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
    }

    private fun isServiceRunning(): Boolean{
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for(service in activityManager.getRunningServices(Int.MAX_VALUE)){
            if(TestService::class.java.name == service.service.className){
                return true
            }
        }
        return false
    }

    fun discoverPeers(){
        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {

            override fun onSuccess() {
            }

            override fun onFailure(reasonCode: Int) {
            }
        })
    }
}

@Composable
@Preview(showBackground = true)
fun HomeView() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(text = "P2P Chat", color = firstColor) },
                backgroundColor = fourthColor,
                elevation = 12.dp
            )
        },
    modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            var tabSelected by remember { mutableStateOf(HomeTab.ALL_CHATS) }
            HomeTabBar(
                tabSelected,
                onTabSelected = {tabSelected = it}
            )
            when(tabSelected){
                HomeTab.ALL_CHATS -> ChatsView(List(30) { User((it+1).toString(), "wer") })
                HomeTab.UNREAD -> ChatsView(List(10) {User((it+1).toString(), "wer")})
                HomeTab.PERSONAL -> ChatsView(List(20) {User((it+1).toString(), "wer")})
            }
        }
    }
}

@Composable
fun HomeTabBar(
    tabSelected: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    AppTabBar { tabBarModifier ->
        AppTabs(
            modifier = tabBarModifier,
            titles = HomeTab.values().map { it.name },
            tabSelected = tabSelected,
            onTabSelected = { newTab ->
                onTabSelected(HomeTab.values()[newTab.ordinal])
            }
        )
    }
}

