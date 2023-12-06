package com.example.loginfire

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeQRScreen(
    showPayAlert:()->Unit,
    showRequestAlert:()->Unit,
    payOn:Boolean, back:()->Unit,
    caption:String,
    imageUri: Uri?){
    var qrContent by remember {
        mutableStateOf("")
    }
    var textPrompt by remember {
        mutableStateOf("")
    }
    var moodPrompt by remember {
        mutableStateOf("")
    }
    var qrButtonEnable by remember {
        mutableStateOf(false)
    }
    var resetButtonEnable by remember {
        mutableStateOf(false)
    }
    if(qrContent != "" && textPrompt != "" && moodPrompt != ""){
        qrButtonEnable = true
    }
    if(qrContent != "" || textPrompt != "" || moodPrompt != ""){
        resetButtonEnable = true
    }
    if(caption.isNotEmpty()){
        val parts = caption.split('/')
        textPrompt = parts[0]
        moodPrompt = parts[1]
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF8F8F8))){
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopStart)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 32.dp, // 하단 시작 부분
                        bottomEnd = 32.dp // 하단 끝 부분
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFD900), // 시작 색상
                            Color(0xFFECDB7A)  // 끝 색상
                        )
                    )
                )){
                Icon(painter = painterResource(id = R.drawable.left),
                    tint = Color.White,
                    modifier = Modifier
                        .padding(0.dp, 60.dp)
                        .size(64.dp, 28.dp)
                        .clickable(onClick = back),
                    contentDescription = "leftIcon")
                Text(
                    text = "QR 제작소",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141414),
                        letterSpacing = 0.36.sp,
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(52.dp))
            Text(
                text = "커스텀 QR 제작소",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 25.2.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                    letterSpacing = 0.36.sp,
                ),
                modifier = Modifier.padding(28.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "QR 코드 컨텐츠",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF141414),
                    letterSpacing = 0.32.sp,
                ),
                modifier = Modifier.padding(28.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = qrContent,
                placeholder = {
                    Text(
                        text = "QR 코드 URL 및 해당되는 정보를 입력해주세요",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9A9393),
                            letterSpacing = 0.24.sp,
                        )
                    )
                },
                onValueChange = {qrContent = it},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF8F8F8),
                    focusedTextColor = Color(0xFF141414),
                    unfocusedTextColor = Color(0xFF141414),
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp, 0.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .border(
                        width = 0.dp,
                        color = Color(0xFFA9A9A9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .clip(RoundedCornerShape(size = 15.dp))
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "텍스트 프롬프트",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF141414),
                    letterSpacing = 0.32.sp,
                ),
                modifier = Modifier.padding(28.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = textPrompt,
                placeholder = {
                    Text(
                        text = "붉은색, 푸른색, 주사위 모양, 심플하고 귀여운 느낌",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9A9393),
                            letterSpacing = 0.24.sp,
                        )
                    )
                },
                onValueChange = { textPrompt = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF8F8F8),
                    focusedTextColor = Color(0xFF141414),
                    unfocusedTextColor = Color(0xFF141414),
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp, 0.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .border(
                        width = 0.dp,
                        color = Color(0xFFA9A9A9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .clip(RoundedCornerShape(size = 15.dp))
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "분위기 프롬프트",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF141414),
                    letterSpacing = 0.32.sp,
                ),
                modifier = Modifier.padding(28.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = moodPrompt,
                placeholder = {
                    Text(
                        text = "상큼 발랄, 원색 계열의 자연친화적인 느낌",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9A9393),
                            letterSpacing = 0.24.sp,
                        )
                    )
                },
                onValueChange = { moodPrompt = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFF8F8F8),
                    focusedTextColor = Color(0xFF141414),
                    unfocusedTextColor = Color(0xFF141414),
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp, 0.dp)
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .border(
                        width = 0.dp,
                        color = Color(0xFFA9A9A9),
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .clip(RoundedCornerShape(size = 15.dp))
            )
        }
        Button(onClick = {
            qrContent = ""
            textPrompt = ""
            moodPrompt = ""
            resetButtonEnable = false
            qrButtonEnable = false
        },
            modifier = Modifier
                .padding(40.dp, 56.dp)
                .width(160.dp)
                .height(52.dp)
                .shadow(
                    elevation = 5.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000),
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.BottomStart),
            shape = RoundedCornerShape(size=10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD900),
                disabledContainerColor = Color(0xFFE4E4E4)
            ),
            enabled = resetButtonEnable
        ) {
            Text(
                text = "초기화",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                    textAlign = TextAlign.Center,
                )
            )
        }
        Button(
            onClick = {
                if(caption.isNotEmpty()){
                    if(imageUri!=null){
                        uploadImageAndResult(imageUri, caption, qrContent, textPrompt, moodPrompt)
                    }
                }
                if(!payOn){
                  showRequestAlert()
                }else{
                  showPayAlert()
                }
        },
            modifier = Modifier
                .padding(40.dp, 56.dp)
                .width(160.dp)
                .height(52.dp)
                .shadow(
                    elevation = 5.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000),
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.BottomEnd),
            shape = RoundedCornerShape(size=10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD900),
                disabledContainerColor = Color(0xFFE4E4E4)
            ),
            enabled = qrButtonEnable
        ) {
            Text(
                text = "QR 제작 하기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

// 이미지와 결과 값을 업로드하는 함수 추가
fun uploadImageAndResult(imageUri: Uri, resultText: String, qrContent: String, textPrompt: String, moodPrompt: String) {
    // 이미지 업로드
    val imageFileName = "image_${System.currentTimeMillis()}.jpg"
    val imageStorageRef = FirebaseStorage.getInstance().reference.child("uploads/$imageFileName")
    val imageUploadTask = imageStorageRef.putFile(imageUri)

    imageUploadTask.addOnSuccessListener { taskSnapshot ->
        imageStorageRef.downloadUrl.addOnSuccessListener { downloadUri ->
            Log.d(ContentValues.TAG, "이미지 업로드 성공: $downloadUri")
            // Firebase Realtime Database에 이미지 URI와 결과 텍스트 저장
            val databaseRef = FirebaseDatabase.getInstance("your-firebase-realtimedb").getReference("analysisResults")
            val uploadId = databaseRef.push().key // 고유한 ID 생성
            val parts = resultText.split('/')
            val uploadRecord = mapOf(
                "imageUrl" to downloadUri.toString(),
                "prompt" to parts.getOrNull(2),
                "moodprompt" to parts.getOrNull(3),
                "qrContent" to qrContent,
                "qrTextPrompt" to textPrompt,
                "qrMoodPrompt" to moodPrompt
            )
            if (uploadId != null) {
                databaseRef.child(uploadId).setValue(uploadRecord).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "데이터베이스 업로드 성공")
                    } else {
                        Log.e(ContentValues.TAG, "데이터베이스 업로드 실패", task.exception)
                    }
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.e(ContentValues.TAG, "이미지 업로드 실패", exception)
    }
}