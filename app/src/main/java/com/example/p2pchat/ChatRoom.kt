@file:Suppress("DEPRECATION")

package com.example.p2pchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.p2pchat.data.Message
import com.example.p2pchat.data.User
import com.example.p2pchat.ui.theme.P2pChatTheme
import com.example.p2pchat.ui.theme.firstColor
import com.example.p2pchat.ui.theme.fourthColor
import com.example.p2pchat.ui.theme.thirdColor

class ChatRoom : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2pChatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val user = intent.extras?.getSerializable("user")
                    if (user != null) {
                        ChatView(user as User)
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ChatView(user: User = User("John", "Doe")) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(text = user.name, color = firstColor) },
                backgroundColor = fourthColor,
                elevation = 12.dp
            )
        },
        bottomBar = { SendMessageView() },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MessageView()
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MessageView(messages: List<Message> = List(10) { Message("Hello", "John", "Doe") }) {
    LazyColumn {
        itemsIndexed(messages) { _, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .border(1.dp, Color.LightGray)
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = item.message,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            color = thirdColor,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            text = item.message,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                            color = thirdColor,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxSize()
                        )
                    }
                    Text(
                        text = item.message,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = thirdColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SendMessageView() {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            shape = CircleShape,
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth(0.80f).padding(8.dp),
        )
        Button(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            modifier = Modifier.padding(8.dp).size(50.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
//            Text("Send")
        }
    }
}