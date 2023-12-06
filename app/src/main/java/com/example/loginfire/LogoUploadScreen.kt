package com.example.loginfire

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LogoUploadScreen(back:()->Unit, makeQROn:(String) -> Unit, uriSender:(Uri) -> Unit){
    var screen:Screen by remember {
        mutableStateOf(Screen.INPUT)
    }
    var caption by remember {
        mutableStateOf("")
    }
    //dark.json 불러올거다
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )
    // 로티 애니메이션 컨트롤하기 위한 부분 progress 설정
    val progress by animateLottieCompositionAsState(
        // 무엇을 만들건지
        composition,
        // iterations - 재생횟수  Iterates Forever = LottieConstants.IterateForever
        iterations = LottieConstants.IterateForever,
        //속도(부호 = 방향)
        speed = 1.0f
    )
    var buttonEnable by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    if(imageUri != null){
        buttonEnable = true
    }
    // 갤러리에서 이미지를 선택하는 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }


    // 카메라 앱에서 사진을 촬영하는 런처
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            // 촬영된 이미지를 임시 파일로 저장하고, 해당 Uri를 얻습니다.
            val tempUri = bitmapToFile(it, context)
            imageUri = tempUri // imageUri 상태를 새로운 Uri로 업데이트
        }
    }

    AnimatedContent(
        targetState = screen,
        modifier = Modifier
            .fillMaxWidth(),
        transitionSpec = {
            slideInHorizontally(initialOffsetX = {
                -it
            }) togetherWith slideOutHorizontally(
                targetOffsetX = {
                    it
                }
            )
        }, label = ""
    ){
        if(screen == Screen.INPUT){
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
                            text = "이미지 분석",
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
                    Text(
                        text = "자신의 로고 / 광고 포스터를\n업로드해보세요",
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
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "이미지 불러오기",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.galleryimage),
                            contentDescription = "갤러리 이미지",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.699999809265137.dp,
                                    spotColor = Color(0x40000000),
                                    ambientColor = Color(0x40000000)
                                )
                                .border(
                                    width = 0.dp,
                                    color = Color(0xFFA9A9A9),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .width(292.dp)
                                .height(40.dp)
                                .background(
                                    color = Color(0xFFFBFBFB),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                }
                        ){
                            Text(
                                text = "분석하고 싶은 이미지를 넣어주세요",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.8.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF9A9393),
                                    letterSpacing = 0.24.sp,
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "사진 찍기",
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cameraimage),
                            contentDescription = "촬영 이미지",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.699999809265137.dp,
                                    spotColor = Color(0x40000000),
                                    ambientColor = Color(0x40000000)
                                )
                                .border(
                                    width = 0.dp,
                                    color = Color(0xFFA9A9A9),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .width(292.dp)
                                .height(40.dp)
                                .background(
                                    color = Color(0xFFFBFBFB),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .clickable {
                                    cameraLauncher.launch()
                                }
                        ){
                            Text(
                                text = "분석하고 싶은 이미지를 찍어주세요",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 16.8.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF9A9393),
                                    letterSpacing = 0.24.sp,
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "이미지 확인하기",
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

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .fillMaxWidth()
                            .height(160.dp)
                            .shadow(
                                elevation = 4.699999809265137.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000)
                            )
                            .border(
                                width = 0.dp,
                                color = Color(0xFFA9A9A9),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .background(
                                color = Color(0xFFE4E4E4),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                    ){
                        // 선택된 이미지를 화면에 표시
                        imageUri?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "선택된 이미지",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        imageUri = null
                        buttonEnable = false
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
                    enabled = buttonEnable
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
                        // 이미지 분석을 시작합니다.
                        imageUri?.let { uri ->
                            sendImageToServerAsJson(uri, context) { newCaption ->
                                caption = newCaption
                                screen = Screen.RESULT
                            }
                        }
                        screen = Screen.LOADING
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
                    enabled = buttonEnable
                ) {
                    Text(
                        text = "다음",
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
        }else if(screen == Screen.LOADING){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF8F8F8))){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(300.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "로고 분석 진행",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 25.2.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF141414),
                            letterSpacing = 0.36.sp,
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "QR 코드는 최초 1회 한에 무료로 진행하고 있습니다.\n현재 많은 양의 요청으로 알림 받기 클릭해주시면\n완료 시 완료 문자 넣어드립니다.",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.8.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF141414),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.24.sp,
                        )
                    )
                }
            }
        }else if(screen == Screen.RESULT){
            LogoResultScreen(
                caption = caption,
                back = back,
                makeQROn = makeQROn,
                uriSender = uriSender,
                uri = imageUri!!)
        }
    }
}

// 이미지를 갤러리에 저장하는 함수
fun saveImageToGallery(context: Context, bitmap: Bitmap) {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg") // 파일명
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") // 파일 형식
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES) // 저장 경로
    }

    // MediaStore를 통해 이미지를 저장
    try {
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.also { uri ->
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                    throw IOException("이미지 저장에 실패했습니다.")
                }
            } ?: throw IOException("OutputStream을 열 수 없습니다.")
        } ?: throw IOException("URI 생성 실패")
        Toast.makeText(context, "이미지가 갤러리에 저장되었습니다.", Toast.LENGTH_LONG).show()
    } catch (e: IOException) {
        Toast.makeText(context, "이미지 저장 중 오류 발생: ${e.message}", Toast.LENGTH_LONG).show()
        Log.e(ContentValues.TAG, "이미지 저장 실패", e)
    }
}



// 3. 이미지 및 결과 데이터 처리 함수 //

fun sendImageToServerAsJson(uri: Uri, context: Context, onCaptionReceived: (String) -> Unit) {
    val base64Image = encodeImageToBase64(uri, context)
    base64Image?.let { base64 ->
        // JSON 객체를 생성합니다.
        val jsonObject = JSONObject()
        jsonObject.put("image", base64)

        // JSON 객체를 JSON 문자열로 변환합니다.
        val jsonString = jsonObject.toString()

        // JSON 문자열을 RequestBody로 변환합니다.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonString.toRequestBody(mediaType)
        val call = RetrofitInstance.service.getImageCaption(requestBody)

        call.enqueue(object : Callback<String> {  // Callback<String> 사용
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val captionText = response.body() ?: ""
                    onCaptionReceived(captionText) // 서버에서 받은 텍스트를 저장하는 부분
                } else {
                    Log.e(ContentValues.TAG, "서버 응답 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(ContentValues.TAG, "통신 실패", t)
            }
        })
    } ?: Log.e(ContentValues.TAG, "Base64 인코딩 실패")
}

// 4. 유틸리티 함수 및 Retrofit 서비스 정의 //

fun bitmapToFile(bitmap: Bitmap, context: Context): Uri? {
    return try {
        // 임시 파일 생성
        val file = File(context.cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
        file.createNewFile()

        // 비트맵을 파일로 변환
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()

        // 파일에 비트맵 데이터 쓰기
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        // 파일의 Uri 반환
        Uri.fromFile(file)
    } catch (e: Exception) {
        Log.e(ContentValues.TAG, "비트맵을 파일로 변환하는데 실패했습니다", e)
        null
    }
}

fun encodeImageToBase64(uri: Uri, context: Context): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        Base64.encodeToString(bytes, Base64.DEFAULT)
    } catch (e: Exception) {
        Log.e(ContentValues.TAG, "이미지 인코딩 실패", e)
        null
    }
}

interface ImageCaptionService {
    @Headers("Content-Type: application/json")
    @POST("isimage/")
    fun getImageCaption(@Body image: RequestBody): Call<String>  // 반환 타입을 String으로 변경
}

object RetrofitInstance {
    val gson = GsonBuilder().setLenient().create()

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(600, TimeUnit.SECONDS) // 연결 타임아웃 시간 설정
        .readTimeout(600, TimeUnit.SECONDS) // 데이터 읽기 타임아웃 시간 설정
        .writeTimeout(600, TimeUnit.SECONDS) // 데이터 쓰기 타임아웃 시간 설정
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("your sever URL") // 서버 URL
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    val service: ImageCaptionService = retrofit.create(ImageCaptionService::class.java)
}

enum class Screen() {
    INPUT, LOADING, RESULT
}
