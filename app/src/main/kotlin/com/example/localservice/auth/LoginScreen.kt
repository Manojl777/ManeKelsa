package com.example.localservice.auth

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.data.UserRepository
import com.example.localservice.i18n.t
import com.example.localservice.ui.ColorAppBg
import com.example.localservice.ui.ColorPrimary
import com.example.localservice.ui.ColorSurface
import com.example.localservice.ui.ColorTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    userRepository: UserRepository,
    onNavigate: (String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val tr: (String) -> String = { t(language, it) }

    Scaffold(
        containerColor = ColorAppBg,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 44.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = {
                        onDarkModeChange(!darkMode)
                    },
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    modifier = Modifier.size(50.dp)
                ) {
                    IconButton(
                        onClick = { onDarkModeChange(!darkMode) }
                    ) {
                        Icon(
                            imageVector =
                                if (darkMode)
                                    Icons.Outlined.LightMode
                                else
                                    Icons.Outlined.DarkMode,
                            contentDescription = null,
                            tint = ColorPrimary
                        )
                    }
                }
                Surface(
                    onClick = {
                        onLanguageChange(
                            if (language == "en") "kn" else "en"
                        )
                    },
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { onLanguageChange(if (language == "en") "kn" else "en") }
                ) {
                    Text(
                        text = if (language == "en") tr("langKannada") else tr("langEnglish"),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        fontWeight = FontWeight.SemiBold,
                        color = ColorPrimary,
                        fontSize = 12.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 42.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = ColorPrimary,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.size(86.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Home,
                                    contentDescription = null,
                                    tint = ColorSurface,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = tr("appName"),
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Black,
                            color = ColorPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = tr("tagline"),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            color = ColorTertiary.copy(alpha = 0.72f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = tr("welcomeTitle"),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = tr("welcomeBody"),
                            fontSize = 17.sp,
                            color = ColorTertiary,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Surface(
                            onClick = {
                                // Direct navigation for testing UI flow
                                onNavigate("role_select")
                            },
                            shape = RoundedCornerShape(20.dp),
                            color = ColorSurface,
                            tonalElevation = 2.dp,
                            shadowElevation = 4.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(62.dp)
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "G",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    style = androidx.compose.ui.text.TextStyle(
                                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                            colors = listOf(
                                                androidx.compose.ui.graphics.Color(0xFF4285F4),
                                                androidx.compose.ui.graphics.Color(0xFFDB4437),
                                                androidx.compose.ui.graphics.Color(0xFFF4B400),
                                                androidx.compose.ui.graphics.Color(0xFF0F9D58)
                                            )
                                        )
                                    ),
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(
                                    text = tr("continueGoogle"),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = tr("termsFooter"),
                    fontSize = 12.sp,
                    color = ColorTertiary.copy(alpha = 0.65f),
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
