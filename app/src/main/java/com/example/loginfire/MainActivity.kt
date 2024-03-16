package com.example.loginfire

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginfire.sign_in.GoogleAuthUiClient
import com.example.loginfire.sign_in.ProfileScreen
import com.example.loginfire.sign_in.SignInScreen
import com.example.loginfire.sign_in.SignInViewModel
import com.example.loginfire.sign_in.isNotLogin.ALREADYLOGIN
import com.example.loginfire.sign_in.isNotLogin.NOT
import com.example.loginfire.sign_in.isNotLogin.YET
import com.example.loginfire.ui.theme.LoginFireTheme
import com.example.sunlogo.StartLogo
import com.example.sunlogo.StartLogo2
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    var expandedCardId by mutableStateOf<String?>(null) // 현재 확장된 카드의 ID를 저장하는 변수
}

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val viewModel by viewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KakaoSdk.init(this, "803b34aa784ec1bb0f0ec57f826d84ed")
        setContent {
            LoginFireTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF8F8F8)
                ) {
                    window.apply {
                        setFlags(
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        )
                    }
                    //투명 상단 상태바를 위한 코드
                    if(Build.VERSION.SDK_INT >= 30){
                        WindowCompat.setDecorFitsSystemWindows(window,false)
                    }
                    //키해쉬
//                    var keyHash = Utility.getKeyHash(context = this@MainActivity)
//                    Log.e(TAG, keyHash, null)
                    //QR제작 유료 과금을 위한 다이얼로그를 관리하는 boolean
                    var showPayAlert by remember {
                        mutableStateOf(false)
                    }
                    //QR제작 요청을 위한 다이얼로그를 관리하는 boolean
                    var showRequestAlert by remember {
                        mutableStateOf(false)
                    }
                    //무료 이후 유료 확인을 위한 boolean
                    var payOn by remember {
                        mutableStateOf(false)
                    }
                    //하단 네비게이션 바 선택 상태 관리 변수
                    var homeSelected by remember {
                        mutableStateOf(BarState.HOME)
                    }
                    //로고 애니메이션 끝났는지 확인
                    var onLogoCompleteListner by remember {
                        mutableStateOf(false)
                    }
                    //swing애니메이션이 완료되었는지 확인
                    var swingSwitch by remember {
                        mutableStateOf(false)
                    }
                    //로그인 상태 관리
                    var isLogOut by remember {
                        mutableStateOf(false)
                    }
                    //시작했는지 관리(메인 스크린)
                    var isStart by remember {
                        mutableStateOf(false)
                    }
                    //로고 애니메이션의 완전한 종료를 위한 boolean
                    var logoOff by remember {
                        mutableStateOf(false)
                    }
                    //QR제작 페이지 ON/OFF 관리
                    var makeQROn by remember {
                        mutableStateOf(false)
                    }
                    //로고 Upload 완료했는지 체크 boolean
                    var logoUploadOn by remember {
                        mutableStateOf(false)
                    }
                    //갤러리 페이지 ON/OFF 관리
                    var galleryPageOn by remember {
                        mutableStateOf(false)
                    }
                    //QR제작시 입력값을 받기 위한 변수
                    var caption by remember {
                        mutableStateOf("")
                    }
                    var imageUri:Uri? by remember {
                        mutableStateOf(null)
                    }
                    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                        if (error != null) {
                            toastMessage(applicationContext, "로그인 실패: ${error}")
                        }
                        else if (token != null) {
                            //toastMessage(applicationContext, "로그인 성공: ${token.accessToken}")
                        }
                    }
                    //네비게이션 화면전환을 위한 컨트롤러
                    val navController = rememberNavController()
                    //로그인 상태 관리
                    var isNotLogin by remember {
                        mutableStateOf(YET)
                    }
                    //구글Auth클라이언트를 통해 유저 정보가 null이 아닐시에 이미 로그인으로 처리하고 메인페이지로 이외엔 로그인 안함으로 처리
                    LaunchedEffect(key1 = Unit) {
                        if(googleAuthUiClient.getSignedInUser() != null) {
                            isNotLogin = ALREADYLOGIN
                            navController.navigate("main")
                        }else{
                            isNotLogin = NOT
                        }
                    }
                    //화면전환 관리 네비게이션 호스트, 시작 지점은 SignInScreen
                    NavHost(navController = navController, startDestination = "sign_in") {
                        //로그인 페이지
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                            swingSwitch = true
                                            CoroutineScope(Dispatchers.IO).launch {
                                                delay(1000)
                                                isStart = true
                                            }
                                        }
                                    }
                                }
                            )
                            //로그인 성공상태면 메인 스크린으로 보냄
                            LaunchedEffect(key1 = state.isSignInSuccessful){
                                if(state.isSignInSuccessful) {
//                                    toastMessage(applicationContext,"로그인 성공")

                                    navController.navigate("main"){
                                        popUpTo("main"){
                                            inclusive = true
                                        }
                                    }
                                    viewModel.resetState()
                                }
                            }
                            //만약 로그인 상태가 아니라면 로그인 페이지로 로그인에서 버튼을 눌렀을때의 각각의 동작도 여기서 관리
                            if(isNotLogin == NOT){
                                SignInScreen(
                                    state = state,
                                    onSignInClick = {
                                        lifecycleScope.launch {
                                            val signInIntentSender = googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    },
                                    onKakagoSignInClick =
                                    {
                                        lifecycleScope.launch {
                                            kakaoLogin(context = this@MainActivity,
                                                callback = callback,
                                                goProfile = {
                                                    navController.navigate("main"){
                                                        popUpTo("main"){
                                                            inclusive = true
                                                        }
                                                    }
                                                    swingSwitch = true
                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        delay(1000)
                                                        isStart = true
                                                    }
                                                })
                                        }
                                    },
                                    goProfile = {
                                        navController.navigate("main"){
                                            popUpTo("main"){
                                                inclusive = true
                                            }
                                        }
                                        swingSwitch = true
                                        CoroutineScope(Dispatchers.IO).launch {
                                            delay(1000)
                                            isStart = true
                                        }
                                    },
                                    onGithubClick = {
                                        signInGithub(this@MainActivity, signUpDone = {
                                            navController.navigate("main"){
                                                popUpTo("main"){
                                                    inclusive = true
                                                }
                                            }
                                            swingSwitch = true
                                            CoroutineScope(Dispatchers.IO).launch {
                                                delay(1000)
                                                isStart = true
                                            }
                                        })
                                    },
                                    logoDone = onLogoCompleteListner
                                )
                            }
                        }
                        //메인페이지
                        composable("main"){
                            homeSelected = BarState.HOME
                            MainScreen(isStart = isStart,
                                logoOff = {logoOff = true},
                                finish = {finish()},
                                context = applicationContext,
                                QRPage = {
                                    makeQROn = true
                                },
                                logoUploadPage = {
                                    logoUploadOn = true
                                },
                                galleryPage = {
                                    galleryPageOn = true
                                })
                        }
                        //프로필
                        composable("profile"){
                            homeSelected = BarState.MYPAGE
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    onLogoCompleteListner = false
                                    isLogOut = !isLogOut
                                    isStart = false
                                    swingSwitch = false
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "로그아웃 완료",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        isNotLogin = NOT
                                        navController.navigate("sign_in"){
                                            popUpTo("sign_in"){
                                                inclusive = true
                                            }
                                        }
                                    }
                                },
                                isStart = isStart)
                        }
                        composable("chatbot"){
                            homeSelected = BarState.CHATBOT
                            ChatBotScreen()
                        }
                        composable("noti"){
                            homeSelected = BarState.NOTIFICATION
                            NotifiScreen()
                        }
                    }
                    NaviBar(
                        isStart = isStart,
                        goHome = {
                            if(!(homeSelected==BarState.HOME)){
                                navController.navigate("main"){
                                    popUpTo("main"){
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        goChatBot = {
                            if(!(homeSelected==BarState.CHATBOT)){
                                navController.navigate("chatbot"){
                                    popUpTo("chatbot"){
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        goNoti = {
                            if(!(homeSelected==BarState.NOTIFICATION)){
                                navController.navigate("noti"){
                                    popUpTo("noti"){
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        goMypage = {
                            if(!(homeSelected==BarState.MYPAGE)){
                                navController.navigate("profile"){
                                    popUpTo("profile"){
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        homeSelected = homeSelected
                    )
                    ////////애니메이션/////////
                    if(isNotLogin == NOT){
                        if(isLogOut){
                            StartLogo(
                                logoComplete = {
                                    onLogoCompleteListner = true
                                },
                                swingSwitch = swingSwitch
                            )
                        }else{
                            StartLogo(
                                logoComplete = {
                                    onLogoCompleteListner = true
                                },
                                swingSwitch = swingSwitch
                            )
                        }
                    }else if(isNotLogin == ALREADYLOGIN) {
                        StartLogo2 {
                            isStart = true
                        }
                    }
                    ////////다이얼로그////////
                    if(makeQROn){
                        Dialog(
                            onDismissRequest = { makeQROn = false },
                            properties = DialogProperties(usePlatformDefaultWidth = false)) {
                            MakeQRScreen(showPayAlert = {
                                showPayAlert = true
                            },
                                showRequestAlert = {
                                    showRequestAlert = true
                                },
                                payOn = payOn,
                                back = {makeQROn = false},
                                caption = caption,
                                imageUri = imageUri)
                        }
                    }else if(logoUploadOn){
                        Dialog(
                            onDismissRequest = { logoUploadOn = false },
                            properties = DialogProperties(usePlatformDefaultWidth = false)) {
                            LogoUploadScreen(
                                back = {logoUploadOn = false},
                                makeQROn = {
                                    caption = it
                                    logoUploadOn = false
                                    makeQROn = true
                                },
                                uriSender = {
                                    imageUri = it
                                })
                        }
                    }else if(galleryPageOn){
                        Dialog(
                            onDismissRequest = { galleryPageOn = false },
                            properties = DialogProperties(usePlatformDefaultWidth = false)) {
                            GalleryScreen(viewModel = viewModel,  back = {logoUploadOn = false})
                        }
                    }
                    if (showPayAlert) {
                        AlertDialog(
                            onDismissRequest = { showPayAlert = false },
                            title = { Text("- 프리미엄 결제 -", fontFamily = FontFamily(Font(R.font.pretendbold)), fontSize = 16.sp) },
                            text = {
                                Text(
                                    "첫 이용 후엔 결제가 필요합니다.",
                                    fontFamily = FontFamily(Font(R.font.pretendard)),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.size(260.dp, 28.dp)
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    showPayAlert = false
                                }) {
                                    Text("결제", color = Color(0xFF141414))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showPayAlert = false }) {
                                    Text("닫기", fontFamily = FontFamily(Font(R.font.pretendard)), color = Color(0xFF141414))
                                }
                            },
                            containerColor = Color(0xFFF8F8F8),
                            titleContentColor = Color(0xFF141414),
                            textContentColor = Color(0xFF141414),
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.dollar),
                                    contentDescription = "돈",
                                    tint = Color(0xFF141414),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            iconContentColor = Color(0xFF141414)
                        )
                    }else if(showRequestAlert){
                        payOn = true
                        AlertDialog(
                            onDismissRequest = { showRequestAlert = false },
                            title = { Text("- 신청 완료 -", fontFamily = FontFamily(Font(R.font.pretendbold)), fontSize = 16.sp) },
                            text = {
                                Text(
                                    "1주일 정도가 소요됩니다.",
                                    fontFamily = FontFamily(Font(R.font.pretendard)),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.size(260.dp, 28.dp)
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    showRequestAlert = false
                                    makeQROn = false
                                }) {
                                    Text("확인", color = Color(0xFF141414))
                                }
                            },
                            containerColor = Color(0xFFF8F8F8),
                            titleContentColor = Color(0xFF141414),
                            textContentColor = Color(0xFF141414),
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "돈",
                                    tint = Color(0xFF141414),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            iconContentColor = Color(0xFF141414)
                        )
                    }
                }
            }
        }
    }
}

//카카오 로그인 함수
fun kakaoLogin(context:Context, callback:(OAuthToken?, Throwable?) -> Unit, goProfile: () -> Unit){
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                toastMessage(context, "로그인 실패: ${error}")
                Log.e(TAG, "카카오톡으로 로그인 실패", error)

                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                //깃발token
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        var scopes = mutableListOf<String>()

                        if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
                        if (user.kakaoAccount?.profileNeedsAgreement == true) { scopes.add("profile") }


                        if (scopes.count() > 0) {
                            Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")
                            //scope 목록을 전달하여 카카오 로그인 요청
                            UserApiClient.instance.loginWithNewScopes(context, scopes) { token, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 추가 동의 실패", error)
                                } else {
                                    Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                                    // 사용자 정보 재요청
                                    UserApiClient.instance.me { user, error ->
                                        if (error != null) {
                                            Log.e(TAG, "사용자 정보 요청 실패", error)
                                        }
                                        else if (user != null) {
                                            Log.i(TAG, "사용자 정보 요청 성공")
                                        }
                                    }
                                }
                            }
                        }
                        val email = "KAKAO${user.kakaoAccount?.email}" ?: "kakaoExample@kakao.com"
                        val uid = "kakaoA${user.id}"
                        //카카오에서 받아온 유저 정보를 firebase auth에서 한 번에 관리를 위해 그 이메일과 uid로 등록
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, uid)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val fireUser = FirebaseAuth.getInstance().currentUser
                                    val displayName = user.kakaoAccount?.profile?.nickname ?: "라이즈 고객1"
                                    val profilePicUrl = user.kakaoAccount?.profile?.profileImageUrl ?: "https://picpac.kr/common/img/default_profile.png"
                                    // 사용자 정보 업데이트
                                    val profileUpdates = UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .setPhotoUri(Uri.parse(profilePicUrl))
                                        .build()

                                    fireUser?.updateProfile(profileUpdates)
                                        ?.addOnCompleteListener { profileUpdateTask ->
                                            if (profileUpdateTask.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "회원가입 완료",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                goProfile()
                                            } else {
                                                toastMessage(context, "회원가입 실패")
                                            }
                                        }
                                }else {
                                    // 회원가입 실패 시 실행되는 부분
                                    val errorCode = (task.exception as FirebaseAuthException).errorCode
                                    if(errorCode == "ERROR_INVALID_EMAIL"){
                                        toastMessage(context, "이메일 형식이 올바르지 않습니다")
                                    }else if(errorCode == "ERROR_WEAK_PASSWORD"){
                                        toastMessage(context, "비밀번호가 6자리 이상이어야 합니다")
                                    }else if(errorCode=="ERROR_EMAIL_ALREADY_IN_USE"){
                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, uid)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
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
                                    Log.e(TAG,errorCode,null)
                                }
                            }
                    }
                }
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }

}
//깃헙 로그인 함수
fun signInGithub(activity: Activity, signUpDone: () -> Unit){
    val firebaseAuth = FirebaseAuth.getInstance()
    val provider = OAuthProvider.newBuilder("github.com")
    val pendingResultTask = firebaseAuth.pendingAuthResult
    if (pendingResultTask != null) {
        // There's something already here! Finish the sign-in for your user.
        pendingResultTask
            .addOnSuccessListener {
                val user = it.user
                // 사용자 정보 업데이트
                if(user != null){
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(user.displayName)
                        .setPhotoUri(user.photoUrl)
                        .build()

                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener { profileUpdateTask ->
                            if(profileUpdateTask.isSuccessful){
                                Log.e(TAG, "깃헙 회원가입 성공?", null)
                                signUpDone()
                            }else{
                                Log.e(TAG, "깃헙 회원가입 실패?", null)
                                toastMessage(activity, "회원가입 실패")
                            }
                        }
                }else{
                    Log.e(TAG, "User Null", null)
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "깃헙 펜딩 실패?: ${it.message}", it)
            }
    } else {
        // There's no pending result so you need to start the sign-in flow.
        // See below.
    }
    firebaseAuth
        .startActivityForSignInWithProvider(activity, provider.build())
        .addOnSuccessListener {
            val user = it.user
            // 사용자 정보 업데이트
            if(user != null){
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(user.displayName)
                    .setPhotoUri(user.photoUrl)
                    .build()

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { profileUpdateTask ->
                        if(profileUpdateTask.isSuccessful){
                            signUpDone()
                            Log.e(TAG, "깃헙 회원가입 성공?", null)
                        }else{
                            Log.e(TAG, "깃헙 회원가입 실패?", null)
                            toastMessage(activity, "회원가입 실패")
                        }
                    }
            }else{
                Log.e(TAG, "User Null", null)
            }
        }
        .addOnFailureListener {
            Log.e(TAG, "깃헙 프로바이더 실패?: ${it.message}", it)
        }
}

