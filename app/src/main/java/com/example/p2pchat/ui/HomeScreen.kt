package com.example.p2pchat.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.p2pchat.ChatRoom
import com.example.p2pchat.R
import com.example.p2pchat.data.User
import com.example.p2pchat.ui.theme.firstColor
import com.example.p2pchat.ui.theme.fourthColor
import com.example.p2pchat.ui.theme.thirdColor

@Composable
@Preview(showBackground = true)
fun HomeScreen(activityKiller: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .background(firstColor)
            .fillMaxSize()
    ) {
        Column {
            GreetingSection()
            Lists(activityKiller = activityKiller)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GreetingSection(
    name: String = "User"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Hello, $name",
                style = MaterialTheme.typography.h5,
                color = fourthColor
            )
            Text(
                text = "Welcome to P2P Chat",
                style = MaterialTheme.typography.body1,
                color = fourthColor
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun Lists(items: List<User> = List(50) {User((it+1).toString(), "wer")}, activityKiller: () -> Unit = {}){
    val mContext = LocalContext.current
    LazyColumn {
        itemsIndexed(items){ _, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        Intent(mContext, ChatRoom::class.java).also{
                            mContext.startActivity(it)
                        }
                    }
            ) {
                SubcomposeAsyncImage(
                    model = "https://smaller-pictures.appspot.com/images/dreamstime_xxl_65780868_small.jpg",
                    loading = {
                        CircularProgressIndicator(modifier = Modifier
                            .fillMaxSize(0.5f)
                            .fillMaxWidth(0.5f))
                    },
                    error = {
                        painterResource(id = R.drawable.search)

                    },
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Text(
                    text = item.name,
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
