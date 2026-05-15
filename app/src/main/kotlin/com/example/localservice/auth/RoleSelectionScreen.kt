package com.example.localservice.auth

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.localservice.R
import com.example.localservice.data.UserRepository
import com.example.localservice.i18n.t
import com.example.localservice.ui.ColorAppBg
import com.example.localservice.ui.ColorPrimary
import com.example.localservice.ui.ColorSecondary
import com.example.localservice.ui.ColorSurface
import com.example.localservice.ui.ColorTertiary
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    userRepository: UserRepository,
    onNavigate: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var busy by remember { mutableStateOf(false) }
    val tr: (String) -> String = { t(language, it) }
    val activity = LocalContext.current as android.app.Activity
    val context = LocalContext.current

    fun switchAccount() {
        scope.launch {
            userRepository.signOutFirebase()
            val webClientId = context.getString(R.string.default_web_client_id)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
            GoogleSignIn.getClient(activity, gso).signOut().await()
            onNavigate("login")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onDarkModeChange(!darkMode) }) {
                Icon(
                    imageVector = if (darkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                    contentDescription = null,
                    tint = ColorPrimary
                )
            }
            Surface(
                onClick = { onLanguageChange(if (language == "en") "kn" else "en") },
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.padding(start = 8.dp)
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = tr("howUse"),
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = ColorPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = tr("selectProfile"),
            fontSize = 15.sp,
            color = ColorTertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(28.dp))

        fun pickRole(role: String) {
            if (busy) return
            scope.launch {
                busy = true
                try {
                    val user = Firebase.auth.currentUser
                    // Save to repository only if user is logged in, otherwise just navigate for testing
                    if (user != null) {
                        userRepository.saveAfterRoleSelection(
                            uid = user.uid,
                            role = role,
                            email = user.email,
                            displayName = user.displayName,
                            photoUrl = user.photoUrl?.toString()
                        )
                    }
                    onNavigate(if (role == "worker") "register_worker" else "register_resident")
                } finally {
                    busy = false
                }
            }
        }

        RoleCard(
            title = tr("imResident"),
            subtitle = tr("imResidentSub"),
            icon = Icons.Default.Groups,
            iconTint = ColorPrimary,
            iconBg = ColorPrimary.copy(alpha = 0.1f),
            onClick = { pickRole("resident") },
            enabled = !busy
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoleCard(
            title = tr("imWorker"),
            subtitle = tr("imWorkerSub"),
            icon = Icons.Default.Work,
            iconTint = ColorSecondary,
            iconBg = ColorSecondary.copy(alpha = 0.1f),
            onClick = { pickRole("worker") },
            enabled = !busy
        )
        if (busy) {
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator(color = ColorPrimary)
        }
        Spacer(modifier = Modifier.height(40.dp))
        TextButton(onClick = { switchAccount() }) {
            Text(tr("switchAccount"), color = ColorTertiary)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: androidx.compose.ui.graphics.Color,
    iconBg: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = ColorSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = iconBg,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(30.dp))
                }
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(subtitle, fontSize = 13.sp, color = ColorTertiary, lineHeight = 18.sp)
            }
        }
    }
}
