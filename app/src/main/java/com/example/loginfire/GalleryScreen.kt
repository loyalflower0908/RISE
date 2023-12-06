package com.example.loginfire

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.internal.immutableListOf

data class AnalysisResult(
    var imageUrl:String = "",
    var moodprompt: String = "",
    var prompt: String = "",
    var qrContent: String = "",
    var qrTextPrompt: String = "",
    var qrMoodPrompt: String = ""
)

@Composable
fun GalleryScreen(viewModel: SharedViewModel, back: () -> Unit){
    // 상태 변수: 분석 결과 목록
    var analysisResults by remember { mutableStateOf<List<AnalysisResult>>(emptyList()) }
    var qrTempList = immutableListOf(R.drawable.qrimage1,R.drawable.qrimage2,R.drawable.qrimage3)
    val lazyListState = rememberLazyListState()
    // 데이터베이스에서 분석 결과 읽기
    LaunchedEffect(Unit) {
        val databaseRef = FirebaseDatabase.getInstance("your-firebase-realtimedb-link").getReference("analysisResults")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val resultList = mutableListOf<AnalysisResult>()
                snapshot.children.forEach { child ->
                    val result = child.getValue(AnalysisResult::class.java)
                    result?.let { resultList.add(it) }
                }
                analysisResults = resultList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Database read failed", error.toException())
            }
        })
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF8F8F8))){
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
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
                    text = "나만의 갤러리",
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
            Spacer(modifier = Modifier.height(36.dp))
            // 갤러리 UI
            LazyColumn(
                state = lazyListState,
                userScrollEnabled = true
            ) {
                itemsIndexed(analysisResults) { index, result ->
                    if(index>=qrTempList.size){
                        ImageItem(
                            result = result,
                            expanded = viewModel.expandedCardId == result.imageUrl, // 현재 카드가 확장되었는지 확인
                            onClick = {
                                viewModel.expandedCardId = if (viewModel.expandedCardId == result.imageUrl) null else result.imageUrl
                            },
                            qrImage = R.drawable.logo
                        )
                    }else{
                        ImageItem(
                            result = result,
                            expanded = viewModel.expandedCardId == result.imageUrl, // 현재 카드가 확장되었는지 확인
                            onClick = {
                                viewModel.expandedCardId = if (viewModel.expandedCardId == result.imageUrl) null else result.imageUrl
                            },
                            qrImage = qrTempList[index]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(result: AnalysisResult,expanded: Boolean, onClick: () -> Unit, qrImage:Int) {
    var isExpanded by remember { mutableStateOf(expanded) }

    LaunchedEffect(expanded) {
        isExpanded = expanded
    }


    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White, disabledContainerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .shadow(
                elevation = 5.dp,
                shape = CardDefaults.shape,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .border(
                width = 0.dp,
                color = Color(0xFFA9A9A9),
                shape = CardDefaults.shape
            )
    ) {
        Column {
            Image(
                painter = painterResource(id = qrImage),
                contentDescription = "QR 이미지",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
//            Image(
//                painter = rememberAsyncImagePainter(result.imageUrl),
//                contentDescription = "QR 이미지",
//                modifier = Modifier
//                    .height(200.dp)
//                    .fillMaxWidth()
//            )
            // 확장 상태에 따라 텍스트를 표시하거나 숨깁니다.
            if (isExpanded) {
                //내용
                TextSection(title = "1. QR 컨텐츠 :", text = result.qrContent)
                TextSection(title = "2. 텍스트 프롬프트 :", text = result.qrTextPrompt)
                TextSection(title = "3. 분위기 프롬프트 :", text = result.qrMoodPrompt)
                TextSection(title = "4. 로고 설명 추출 번역 :", text = result.prompt)
                TextSection(title = "5. 로고 분위기 추출 번역 :", text = result.moodprompt)
            }
        }
    }
}