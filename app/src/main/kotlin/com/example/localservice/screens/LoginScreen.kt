package com.example.localservice.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.auth.GoogleAuthHelper
import com.example.localservice.data.FirestoreRepository
import com.example.localservice.firebase.FirebaseManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider

private val englishTexts = mapOf(
    "appName" to "Mane Kelsa",
    "tagline" to "Premium Home Services at Your\nDoorstep",
    "welcome" to "Welcome to Mane Kelsa",
    "desc" to "The most trusted platform to find verified home service professionals nearby.",
    "google" to "Continue with Google",
    "footer" to "By continuing, you agree to Mane Kelsa's Terms of Service and Privacy Policy."
)

private val kannadaTexts = mapOf(
    "appName" to "ಮನೆ ಕೆಲಸ",
    "tagline" to "ನಿಮ್ಮ ಮನೆಯ ಬಾಗಿಲಿಗೆ ಪ್ರೀಮಿಯಂ ಮನೆ ಸೇವೆಗಳು",
    "welcome" to "ಮನೆ ಕೆಲಸಕ್ಕೆ ಸ್ವಾಗತ",
    "desc" to "ಹತ್ತಿರದ ಪರಿಶೀಲಿಸಿದ ಮನೆ ಸೇವಾ\nವೃತ್ತಿಪರರನ್ನು ಹುಡುಕಲು ಅತ್ಯಂತ\nವಿಶ್ವಾಸಾರ್ಹ ವೇದಿಕೆ.",
    "google" to "ಗೂಗಲ್ ಮೂಲಕ ಮುಂದುವರಿಯಿರಿ",
    "footer" to "ಮುಂದುವರಿಯುವ ಮೂಲಕ, ನೀವು ಮನೆ ಕೆಲಸದ ಸೇವಾ\nನಿಯಮಗಳನ್ನು ಮತ್ತು ಗೌಪ್ಯತಾ ನೀತಿಯನ್ನು ಒಪ್ಪುತ್ತೀರಿ."
)

fun getLoginText(language: String, key: String): String {
    return if (language == "kn") {
        kannadaTexts[key] ?: ""
    } else {
        englishTexts[key] ?: ""
    }
}

@Composable
fun LoginScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onNavigate: (String) -> Unit
) {

    val context = LocalContext.current

    val googleAuthHelper = remember {
        GoogleAuthHelper(context)
    }

    val firestoreRepository = remember {
        FirestoreRepository()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            try {

                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(
                        result.data
                    )

                val account =
                    task.getResult(
                        com.google.android.gms.common.api.ApiException::class.java
                    )

                val credential = GoogleAuthProvider.getCredential(
                    account.idToken,
                    null
                )

                FirebaseManager.auth
                    .signInWithCredential(credential)
                    .addOnCompleteListener { auth ->

                        if (auth.isSuccessful) {

                            val currentUser = FirebaseManager.auth.currentUser

                            val uid = currentUser?.uid ?: return@addOnCompleteListener

                            firestoreRepository.checkResidentExists(uid) { residentExists ->

                                if (residentExists) {

                                    onNavigate("resident_dashboard")

                                } else {

                                    firestoreRepository.checkWorkerExists(uid) { workerExists ->

                                        if (workerExists) {

                                            onNavigate("worker_dashboard")

                                        } else {

                                            onNavigate("role_select")
                                        }
                                    }
                                }
                            }

                        } else {

                            Toast.makeText(
                                context,
                                "Authentication Failed",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

            } catch (e: Exception) {

                Toast.makeText(
                    context,
                    e.message ?: "Google Sign In Failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val backgroundColor =
        if (darkTheme) Color(0xFF05060A)
        else Color(0xFFF7F4EE)

    val cardColor =
        if (darkTheme) Color(0xFF121319)
        else Color.White

    val titleColor =
        if (darkTheme) Color.White
        else Color(0xFF4F4F47)

    val descColor =
        if (darkTheme) Color(0xFF8A8A9A)
        else Color(0xFFA1A19B)

    val tealColor = Color(0xFF006D6F)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            if (darkTheme)
                                Color(0xFF07161B)
                            else
                                Color(0xFFEAF0EB)
                        )
                        .border(
                            1.dp,
                            if (darkTheme)
                                Color(0xFF10323A)
                            else
                                Color(0xFFD4DDD7),
                            CircleShape
                        )
                        .clickable {
                            onThemeChange(!darkTheme)
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = if (darkTheme)
                            Icons.Outlined.LightMode
                        else
                            Icons.Outlined.DarkMode,
                        contentDescription = null,
                        tint = tealColor
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            if (darkTheme)
                                Color(0xFF07161B)
                            else
                                Color(0xFFEAF0EB)
                        )
                        .border(
                            1.dp,
                            if (darkTheme)
                                Color(0xFF10323A)
                            else
                                Color(0xFFD4DDD7),
                            RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            onLanguageChange(
                                if (language == "en") "kn" else "en"
                            )
                        }
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {

                    Text(
                        text = if (language == "en") "ಕನ್ನಡ" else "ENGLISH",
                        color = tealColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(36.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (darkTheme) 0.dp else 8.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 28.dp,
                            vertical =
                                if (language == "kn") 28.dp else 36.dp
                        ),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Top
                ){

                    Surface(
                        modifier = Modifier.size(74.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = tealColor,
                        shadowElevation = 10.dp
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(38.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = getLoginText(language, "appName"),


                        fontSize =
                            if (language == "kn") 30.sp else 28.sp,

                        fontWeight = FontWeight.ExtraBold,

                        color = tealColor,

                        textAlign = TextAlign.Center,

                        lineHeight =
                            if (language == "kn") 38.sp else 34.sp,

                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = getLoginText(language, "tagline"),

                        textAlign = TextAlign.Center,

                        color = descColor,

                        fontSize =
                            if (language == "kn") 17.sp else 16.sp,

                        fontStyle = FontStyle.Italic,

                        lineHeight =
                            if (language == "kn") 30.sp else 24.sp,

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(42.dp))

                    Text(
                        text = getLoginText(language, "welcome"),

                        color = titleColor,

                        fontSize =
                            if (language == "kn") 30.sp else 26.sp,

                        fontWeight = FontWeight.Bold,

                        textAlign = TextAlign.Center,

                        lineHeight =
                            if (language == "kn") 40.sp else 30.sp,

                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = getLoginText(language, "desc"),

                        color = descColor,

                        textAlign = TextAlign.Center,

                        lineHeight =
                            if (language == "kn") 34.sp else 28.sp,

                        fontSize =
                            if (language == "kn") 16.sp else 18.sp,

                        fontWeight = FontWeight.Medium,

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(42.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .clickable {
                                googleAuthHelper.googleSignInClient
                                    .signOut()
                                    .addOnCompleteListener {

                                        launcher.launch(
                                            googleAuthHelper.getSignInIntent()
                                        )
                                    }
                            },
                        shape = RoundedCornerShape(18.dp),
                        color = if (darkTheme)
                            Color(0xFF1E2027)
                        else
                            Color.White,
                        shadowElevation = if (darkTheme) 0.dp else 8.dp
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "G",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                style = TextStyle(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF4285F4),
                                            Color(0xFFDB4437),
                                            Color(0xFFF4B400),
                                            Color(0xFF0F9D58)
                                        )
                                    )
                                )
                            )

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text =
                                    if (language == "en")
                                        "Continue with Google"
                                    else
                                        "ಗೂಗಲ್ ಮೂಲಕ ಮುಂದುವರಿಯಿರಿ",

                                color =
                                    if (darkTheme)
                                        Color.White
                                    else
                                        Color(0xFF4A4A4A),

                                fontWeight = FontWeight.Bold,

                                fontSize =
                                    if (language == "kn")
                                        14.sp
                                    else
                                        18.sp,

                                maxLines = 1
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = getLoginText(language, "footer"),
                color = Color(0xFFC0B6A9),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}