package com.example.loginfire.sign_in

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.loginfire.R
import com.example.loginfire.toastMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    onKakagoSignInClick: () -> Unit,
    goProfile: () -> Unit,
    onGithubClick : () -> Unit,
    logoDone:Boolean
){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginDialog by remember {
        mutableStateOf(false)
    }
    var signUpDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = state.signInError){
        state.signInError?.let{ error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()

        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        AnimatedVisibility(visible = logoDone,
            enter = slideInVertically(tween(2200, easing = FastOutSlowInEasing), initialOffsetY = { it/8 }) + fadeIn(tween(2200, easing = FastOutSlowInEasing))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(240.dp))
                Box(modifier = Modifier
                    .size(450.dp, 60.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(percent = 10),
                        spotColor = Color.Black,
                        ambientColor = Color.Black
                    )
                    .clip(RoundedCornerShape(10))
                    .clickable(onClick = onKakagoSignInClick)){
                    Image(painter = painterResource(id = R.drawable.kakaotemp),
                        contentDescription = "KakaoLogin",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize())
                    Text(text = "카카오 로그인", fontFamily = FontFamily(Font(R.font.pretendbold)), color = Color(0xFF191919), fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier
                    .size(450.dp, 60.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(percent = 10),
                        spotColor = Color.Black,
                        ambientColor = Color.Black
                    )
                    .background(Color(0xFFF2F2F2))
                    .clip(RoundedCornerShape(10))
                    .clickable(onClick = onSignInClick)
                ){
                    Image(painter = painterResource(id = R.drawable.googlelogo),
                        contentDescription = "googlelogo",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(48.dp)
                            .align(Alignment.CenterStart))
                    Text(text = "Google 로그인", fontFamily = FontFamily(Font(R.font.pretendbold)), color = Color(0xFF1F1F1F), fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier
                    .size(450.dp, 60.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(percent = 10),
                        spotColor = Color.Black,
                        ambientColor = Color.Black
                    )
                    .background(Color(0xFFD0021B))
                    .clip(RoundedCornerShape(10))
                    .clickable(onClick = {
                        loginDialog = true
                    })
                ){
                    Icon(imageVector = Icons.Filled.Email,
                        tint = Color(0xFFFBEFF1),
                        contentDescription = "Email Icon",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(32.dp)
                            .align(Alignment.CenterStart))
                    Text(text = "이메일 로그인", fontFamily = FontFamily(Font(R.font.pretendbold)), color = Color(0xFFFBEFF1), fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier
                    .size(450.dp, 60.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(percent = 10),
                        spotColor = Color.Black,
                        ambientColor = Color.Black
                    )
                    .background(Color(0xFF444444))
                    .clip(RoundedCornerShape(10))
                    .clickable(onClick = onGithubClick)
                ){
                    Image(painter = painterResource(id = R.drawable.githubcircle),
                        contentDescription = "githublogo",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(48.dp)
                            .align(Alignment.CenterStart))
                    Text(text = "Github 로그인", fontFamily = FontFamily(Font(R.font.pretendbold)), color = Color(0xFFFFFFFF), fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center))
                }
            }
        }
        if(loginDialog) {
            Dialog(onDismissRequest = { loginDialog = !loginDialog }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                    contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp)
                        .align(Alignment.TopCenter)){
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            tint = Color(0xFFFFD900),
                            modifier = Modifier
                                .padding(12.dp, 0.dp)
                                .size(40.dp)
                                .align(Alignment.CenterStart),
                            contentDescription = "Back")
                        Text(
                            text = "이메일 로그인",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendbold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),
                                letterSpacing = 0.36.sp,
                            ),
                            modifier = Modifier
                                .padding(64.dp, 0.dp)
                                .align(Alignment.CenterStart)
                        )
                        Divider(thickness = 1.dp, color = Color.Gray.copy(0.3f), modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth())
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "이메일") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color(0xFFFBFBFB),
                                focusedTextColor = Color(0xFF141414),
                                unfocusedTextColor = Color(0xFF141414),),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 비밀번호 입력 필드
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "비밀번호") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color(0xFFFBFBFB),
                                focusedTextColor = Color(0xFF141414),
                                unfocusedTextColor = Color(0xFF141414),),
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .width(360.dp)
                                .height(48.dp)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(percent = 10),
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black
                                )
                                .background(Color(0xFFFFD900))
                                .clip(RoundedCornerShape(10))
                                .clickable {
                                    if (email == "" || password == "") {
                                        toastMessage(context, "비어있는 칸이 있습니다")
                                    } else {
                                        signInEmail(
                                            email,
                                            password,
                                            context,
                                            goProfile = goProfile
                                        )
                                    }
                                }
                        ) {
                            Text(
                                text = "로그인",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.pretendbold)),
                                color = Color(0xFF141414),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                            Text(
                                text = "처음 오셨나요?",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.pretendardlight)),
                                color = Color.Gray,
                                modifier = Modifier.clickable { signUpDialog = !signUpDialog }
                            )
                            Divider(thickness = 1.dp, modifier = Modifier
                                .padding(horizontal = 40.dp)
                                .size(1.dp, 20.dp))
                            Text(
                                text = "비밀번호 찾기",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.pretendardlight)),
                                color = Color.Gray,
                                modifier = Modifier.clickable {  }
                            )
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
        if(signUpDialog){
            Dialog(onDismissRequest = { signUpDialog = !signUpDialog }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp)
                        .align(Alignment.TopCenter)){
                        Icon(imageVector = Icons.Filled.ArrowBack,
                            tint = Color(0xFFFFD900),
                            modifier = Modifier
                                .padding(12.dp, 0.dp)
                                .size(40.dp)
                                .align(Alignment.CenterStart),
                            contentDescription = "Back")
                        Text(
                            text = "회원가입",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendbold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF141414),
                                letterSpacing = 0.36.sp,
                            ),
                            modifier = Modifier
                                .padding(64.dp, 0.dp)
                                .align(Alignment.CenterStart)
                        )
                        Divider(thickness = 1.dp, color = Color.Gray.copy(0.3f), modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth())
                    }
                    SignUpScreen(context = context, signUpDone = {
                        signUpDialog = false
                        Toast.makeText(
                            context,
                            "회원가입 완료",
                            Toast.LENGTH_LONG
                        ).show()
                    })
                }
            }
        }
    }
}

fun signInEmail(email:String, password:String, context:Context, goProfile:()->Unit){
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                toastMessage(context, "로그인 성공")
                goProfile()
            } else {
                // 로그인 실패 시 실행되는 부분
                val errorCode = (task.exception as FirebaseAuthException).errorCode
                val errorMessage = task.exception?.message
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
}
