package com.example.localservice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val englishTexts = mapOf(
    "title" to "How will you use Mane Kelsa?",
    "subtitle" to "Select your profile type to continue",
    "residentTitle" to "I'm a Resident",
    "residentDesc" to "I want to book quality home services",
    "workerTitle" to "I'm a Worker",
    "workerDesc" to "I want to provide my professional skills",
    "switch" to "Not you? Switch account"
)

private val kannadaTexts = mapOf(
    "title" to "ನೀವು ಮನೆ ಕೆಲಸವನ್ನು ಹೇಗೆ ಬಳಸುತ್ತೀರಿ?",
    "subtitle" to "ಮುಂದುವರಿಯಲು ನಿಮ್ಮ ಪ್ರೊಫೈಲ್ ಪ್ರಕಾರವನ್ನು ಆಯ್ಕೆ ಮಾಡಿ",
    "residentTitle" to "ನಾನು ನಿವಾಸಿ",
    "residentDesc" to "ನಾನು ಗುಣಮಟ್ಟದ ಮನೆ ಸೇವೆಗಳನ್ನು ಬುಕ್ ಮಾಡಲು ಬಯಸುತ್ತೇನೆ",
    "workerTitle" to "ನಾನು ಕೆಲಸಗಾರ",
    "workerDesc" to "ನಾನು ನನ್ನ ವೃತ್ತಿಪರ ಕೌಶಲ್ಯಗಳನ್ನು ನೀಡಲು ಬಯಸುತ್ತೇನೆ",
    "switch" to "ನೀವಲ್ಲವೇ? ಖಾತೆ ಬದಲಾಯಿಸಿ"
)

private fun getText(
    language: String,
    key: String
): String {

    return if (language == "kn") {
        kannadaTexts[key] ?: ""
    } else {
        englishTexts[key] ?: ""
    }
}

@Composable
fun RoleSelectionScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onResidentClick: () -> Unit,
    onWorkerClick: () -> Unit,
    onSwitchAccount: () -> Unit
) {

    val backgroundColor =
        if (darkTheme) Color(0xFF05060A)
        else Color(0xFFF7F4EE)

    val cardColor =
        if (darkTheme) Color(0xFF14151C)
        else Color.White

    val titleColor =
        if (darkTheme) Color(0xFFFFFFFF)
        else Color(0xFF4E4B43)

    val subtitleColor =
        if (darkTheme) Color(0xFF8D8D96)
        else Color(0xFFA29D94)

    val tealColor = Color(0xFF006D6F)

    val orangeColor = Color(0xFFFF5A1F)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

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

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = getText(language, "title"),
                textAlign = TextAlign.Center,
                color = tealColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = if (language == "kn") 28.sp else 26.sp,
                lineHeight = if (language == "kn") 40.sp else 34.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = getText(language, "subtitle"),
                textAlign = TextAlign.Center,
                color = subtitleColor,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(46.dp))

            // RESIDENT CARD

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onResidentClick()
                    },
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (darkTheme) 0.dp else 6.dp
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (darkTheme)
                        Color(0xFF1E5B5D)
                    else
                        Color(0xFFD7E8E6)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 28.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = RoundedCornerShape(18.dp),
                        color = if (darkTheme)
                            tealColor
                        else
                            Color(0xFFEAF2F1)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.PersonOutline,
                                contentDescription = null,
                                tint = tealColor,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(22.dp))

                    Column {

                        Text(
                            text = getText(language, "residentTitle"),
                            color = titleColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (language == "kn") 22.sp else 20.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = getText(language, "residentDesc"),
                            color = subtitleColor,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // WORKER CARD

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onWorkerClick()
                    },
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (darkTheme) 0.dp else 6.dp
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (darkTheme)
                        Color(0xFF5A2B1F)
                    else
                        Color(0xFFFFD7CA)
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 28.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = RoundedCornerShape(18.dp),
                        color = if (darkTheme)
                            orangeColor
                        else
                            Color(0xFFFFEFE9)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.WorkOutline,
                                contentDescription = null,
                                tint = orangeColor,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(22.dp))

                    Column {

                        Text(
                            text = getText(language, "workerTitle"),
                            color = titleColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (language == "kn") 22.sp else 20.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = getText(language, "workerDesc"),
                            color = subtitleColor,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = getText(language, "switch"),
                color = if (darkTheme)
                    orangeColor
                else
                    Color(0xFFC0B6A9),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                    onSwitchAccount()
                }
            )
        }
    }
}