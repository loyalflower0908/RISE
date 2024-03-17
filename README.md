# ☀️ 프로젝트 제목 : RISE ☀️
&nbsp;

소상공인들을 위해 특색있는 QR코드를 제작해주는 서비스를 중심으로,

신청한 브랜드의 관련 이미지를 분석해서 키워드를 뽑아내고 키워드나 사용자의 명령을 기반으로 특색있는 QR이미지를 생성한다.

&nbsp;

&nbsp;

## 💻 담당한 개발 파트 💻
전체 UI는 디자이너와 Figma를 통한 협업을 통해 UI 디자인을 보고 그대로 개발하였고

애니메이션과 스플래시 스크린 애니메이션 등 동적인 화면을 구성하였습니다.

kakao login API를 통해 카카오 로그인, Firebase Auth를 통해 구글, 이메일, 깃허브 로그인 및 유저 관리와 로그아웃을 개발하였다.

Navigation을 통한 화면 전환, Lottie를 이용해 로딩 화면을 구성하였고 값을 페이지로 넘겨주는 등의 기본 기능들을 구현하였다.

갤러리 페이지와 Retrofit2로 통신하는 파트는 협업을 통해 다른 팀원이 제작하였다.

&nbsp;

&nbsp;

## 📌 프로젝트 구성 📌
blip2와 QR code Monster 모델을 서버에 띄우고(서버 코드는 해당 깃헙에 존재하지 않는다.)


앱에서 REST API 통신을 서버와 주고 받으며 사용자를 위한 UI와 필요 기능 구성.

&nbsp;
_____________________________________________________
### 스크린샷
   
<div style="display: flex; flex-direction: row;">
    <img src="https://github.com/loyalflower0908/RISE/assets/142879940/8409a711-c025-40c6-84a5-29a67fa87d77" width="30%" height="30%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/assets/142879940/e0ee6a9b-ac34-4d10-9bb4-3673cf8d9b59" width="30%" height="30%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/assets/142879940/7e6822ee-af2d-4871-911e-0ad6fb5060e5" width="30%" height="30%" style="margin: 0 10px;">
</div>

<div style="display: flex; flex-direction: row;">
    <img src="https://github.com/loyalflower0908/RISE/assets/142879940/5abf4bfa-d481-4047-a026-d1e26e64fd58" width="30%" height="30%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/assets/142879940/d41de9ed-7963-45cb-8613-e904dfe760d4" width="30%" height="30%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/assets/142879940/3307e75a-dcc5-4be3-b650-f04e87408506" width="30%" height="30%" style="margin: 0 10px;">
</div>

&nbsp;
   
<div style="display: flex; flex-direction: row;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/login%20page.png" width="20%" height="20%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/main%20page.png" width="20%" height="20%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/Analysis%20Page.png" width="20%" height="20%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/Analysis%20Result%20Page.png" width="20%" height="20%" style="margin: 0 10px;">
</div>

<div style="display: flex; flex-direction: row;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/Chatbot%20Page.png" width="20%" height="20%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/notification%20page.png" width="20%" height="20%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/loading%20page.png" width="20%" height="20%" style="margin: 0 10px;">
    <img src="https://github.com/loyalflower0908/RISE/blob/08726c96b56d171ded707a126a1fcf52668b0297/screenshot/gallery%20page.png" width="20%" height="20%" style="margin: 0 10px;">
</div>

&nbsp;

_____________________________________________________
### 📚 기술스택 📚
Jetpack Compose, Firebase Authentication, Firebase Storage, Firebase RealtimeDB, Kakao SDK(Login), Lottie, Retrofit2

&nbsp;

_____________________________________________________
### 🕐 개발 소요 시간 🕐
4일
