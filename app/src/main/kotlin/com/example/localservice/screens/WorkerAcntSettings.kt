package com.example.localservice.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.i18n.t
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val BgColor = Color(0xFFF7F4ED)
private val Orange = Color(0xFFFF5A1F)
private val Teal = Color(0xFF006D6F)

@Composable
fun WorkerAcntSettings(
    language: String,
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {

    val tr: (String) -> String = {
        t(language, it)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    if (showDeleteDialog) {

        DeleteAccountDialog(
            language = language,
            onDismiss = {
                showDeleteDialog = false
            },
            onDeleteConfirmed = {

                val uid =
                    FirebaseAuth.getInstance()
                        .currentUser?.uid

                if (uid != null) {

                    FirebaseFirestore
                        .getInstance()
                        .collection("workers")
                        .document(uid)
                        .delete()

                    FirebaseAuth
                        .getInstance()
                        .currentUser
                        ?.delete()

                    FirebaseAuth
                        .getInstance()
                        .signOut()

                    onLogout()
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    horizontal = 12.dp,
                    vertical = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Text(
                text = tr("settings"),
                color = Teal,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onDarkModeChange(!darkMode)
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F6F6))
            ) {

                Icon(
                    if (darkMode)
                        Icons.Outlined.LightMode
                    else
                        Icons.Outlined.DarkMode,
                    contentDescription = null,
                    tint = Teal
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            shape = RoundedCornerShape(34.dp),
            color = Color.White,
            shadowElevation = 4.dp
        ) {

            Column {

                SettingsRow(
                    iconBg = Color(0xFFFFF1EC),
                    iconColor = Orange,
                    title = tr("switchDarkMode"),
                    onClick = {
                        onDarkModeChange(!darkMode)
                    }
                )

                Divider(
                    color = Color(0xFFF1F1F1)
                )

                SettingsRow(
                    iconBg = Color(0xFFFFF1EC),
                    iconColor = Orange,
                    title = tr("editProfile"),
                    arrow = true,
                    onClick = onEditProfile
                )

                Divider(
                    color = Color(0xFFF1F1F1)
                )

                SettingsRow(
                    iconBg = Color(0xFFFFE5E5),
                    iconColor = Color.Red,
                    title = tr("deleteAccount"),
                    arrow = true,
                    textColor = Color.Red,
                    delete = true,
                    onClick = {
                        showDeleteDialog = true
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsRow(
    iconBg: Color,
    iconColor: Color,
    title: String,
    arrow: Boolean = false,
    delete: Boolean = false,
    textColor: Color = Color(0xFF4B4B45),
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                if (delete)
                    Icons.Default.Delete
                else
                    Icons.Outlined.Person,
                contentDescription = null,
                tint = iconColor
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = title,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        if (arrow) {

            Text(
                text = "›",
                color =
                    if (delete)
                        Color(0xFFFFB0B0)
                    else
                        Color.LightGray,
                fontSize = 28.sp
            )
        }
    }
}

@Composable
private fun DeleteAccountDialog(
    language: String,
    onDismiss: () -> Unit,
    onDeleteConfirmed: () -> Unit
) {

    val tr: (String) -> String = {
        t(language, it)
    }

    var text by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        containerColor = Color.White,
        shape = RoundedCornerShape(40.dp),
        text = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFE5E5)),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "!",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = tr("deleteAccountPermanent"),
                    color = Color(0xFF4B4B45),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 34.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = tr("typeDeleteWarning"),
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {

                        Text(
                            text = "DELETE",
                            color = Color(0xFFFF8C8C),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color(0xFFEAEAEA)
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(62.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF8F6EE)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Text(
                            text = tr("cancel"),
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        onClick = {

                            if (text == "DELETE") {

                                onDeleteConfirmed()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(62.dp),
                        enabled = text == "DELETE",
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Text(
                            text = tr("yesDelete"),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    )
}