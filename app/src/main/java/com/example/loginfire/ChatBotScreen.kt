package com.example.loginfire
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatchat.CachedChatBot
import com.example.chatchat.ChatBot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ChatBotScreen(){
    // Create LazyListState to manage scrolling
    val scrollState = rememberLazyListState()
    val key = "Your-OpenAI-Key" // TODO Add your key here
    val system = "너의 이름은 RISE봇 이야. 브랜드 컨설팅을 해주는 역할이야. 이용자가 요청했을 시에 로고명이나 슬로건을 추천해주거나 로고 디자인을 해줬으면 좋겠어. 색감 추천을 원할 시에 브랜드에서 하는 일을 물어보고 거기에 맞게 색감 추천을 해줘."
    val request = ChatBot.ChatCompletionRequest("gpt-4", system)//gpt-3.5-turbo
    val bot = CachedChatBot(key, request)
    var response by remember { mutableStateOf("") }
    var displayedMessages by remember { mutableStateOf(mutableListOf<Message>()) }
    var newMessage by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Spacer(modifier = Modifier.height(32.dp))
//            Row(modifier = Modifier.padding(16.dp)) {
//                Icon(
//                    imageVector = Icons.Outlined.KeyboardArrowLeft,
//                    contentDescription = "back",
//                    tint = Color.Black,
//                    modifier = Modifier
//                        .align(
//                            Alignment.CenterVertically
//                        )
//                        .size(32.dp)
//                        .clickable(onClick = {
//                            popBack()
//                        })
//                )
//                Spacer(modifier = Modifier.size(24.dp, 0.dp))
//                Text(
//                    text = "챗봇",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    color = Color(0xFF141414),
//                    fontFamily = FontFamily(Font(R.font.pretendard)),
//                    modifier = Modifier.align(
//                        Alignment.CenterVertically
//                    )
//                )
//            }
            Image(painter = painterResource(id = R.drawable.gpt),
                contentScale = ContentScale.Fit,
                modifier = Modifier.align(Alignment.CenterHorizontally).size(120.dp,80.dp),
                contentDescription = "gpt")
            Divider(color = Color(0xFF141414).copy(0.2f))
            Spacer(modifier = Modifier.size(0.dp, 16.dp))
            Column(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .background(Color.Transparent), // Expand to take remaining space
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    state = scrollState // Assign LazyListState to LazyColumn
                ) {
                    items(displayedMessages) { message ->
                        val isCurrentUserMessage = (!message.isChatBot)
                        val arrangement =
                            if (isCurrentUserMessage) Arrangement.End else Arrangement.Start
                        val textBackgroundColor =
                            if (isCurrentUserMessage) Color(0xFFFFEB3B) else Color.DarkGray
                        val textColor = if (isCurrentUserMessage) Color.Black else Color.White
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            if(message.isChatBot){
                                Text(
                                    buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                                fontWeight = FontWeight(800),
                                                color = Color(0xFFFD9F28),
                                                letterSpacing = 0.32.sp
                                            )
                                        ) {
                                            append("RISE")
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.pretendard)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF141414),
                                                letterSpacing = 0.32.sp
                                            )
                                        ) {
                                            append(" BOT")
                                        }
                                    },
                                    lineHeight = 22.4.sp,
                                    modifier = Modifier.padding(28.dp, 0.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = arrangement,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Box(modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(horizontal = 12.dp)
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(size = 15.dp))
                                    .background(textBackgroundColor)){
                                    message.text?.let {
                                        Text(
                                            text = it,
                                            textAlign = TextAlign.Start,
                                            color = textColor,
                                            modifier = Modifier
                                                .padding(16.dp, 12.dp)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = newMessage,
                        onValueChange = {
                            newMessage = it
                                        },
                        placeholder = { Text(text = "채팅 입력", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                BorderStroke(
                                    width = 1.dp,
                                    color = Color(0xFF141414)
                                ), shape = RoundedCornerShape(
                                    10
                                )
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedTextColor = Color(0xFF141414),
                            unfocusedTextColor = Color(0xFF141414),
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledTextColor = Color.Gray,
                            disabledPlaceholderColor = Color.Gray,
                            cursorColor = Color(0xFF141414)
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = {
                        if (newMessage.isNotBlank()) {
                            val currentMessage = newMessage
                            displayedMessages = displayedMessages.toMutableList().apply {
                                add(Message(text = currentMessage, isChatBot = false))
                            }
                            CoroutineScope(Dispatchers.Main).launch {
                                scrollState.scrollToItem(displayedMessages.size - 1)
                            }
                            try {
                                Thread{
                                    response = bot.generateResponse(currentMessage)
                                    displayedMessages = displayedMessages.toMutableList().apply {
                                        add(Message(text = response, isChatBot = true))
                                    }
                                    CoroutineScope(Dispatchers.Main).launch {
                                        scrollState.scrollToItem(displayedMessages.size - 1)
                                    }
                                }.start()
                            } catch (e: SocketTimeoutException) {
                                response = "요청 시간 초과"
                                displayedMessages = displayedMessages.toMutableList().apply {
                                    add(Message(text = response, isChatBot = true))
                                }
                            } catch (e: Exception) {
                                // 다른 예외 처리
                                e.printStackTrace()
                            }
                            newMessage = "" // Clear the input
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Send,
                            contentDescription = "SEND",
                            tint = Color(0xFF1E92E9),
                            modifier = Modifier.size(48.dp))
                    }
                }
                Spacer(modifier = Modifier.height(160.dp))
            }
        }
    }
}

data class Message(
    val text: String? = "메세지 오류",
    val isChatBot:Boolean
)
