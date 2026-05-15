package com.example.localservice.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.ui.*

@Composable
fun SettingsScreen(
    darkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    language: String,
    onLanguageChange: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAppBg)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Settings",
            style = MaterialTheme.typography.displaySmall,
            color = ColorPrimary
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorSurface
            ),
            border = BorderStroke(
                1.dp,
                ColorTertiary.copy(alpha = 0.06f)
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                // DARK MODE

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = ColorPrimary.copy(alpha = 0.10f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.DarkMode,
                                contentDescription = null,
                                tint = ColorPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Dark Mode",
                            fontWeight = FontWeight.Bold,
                            color = ColorTertiary
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Enable dark appearance",
                            fontSize = 12.sp,
                            color = ColorTertiary.copy(alpha = 0.55f)
                        )
                    }

                    Switch(
                        checked = darkMode,
                        onCheckedChange = {
                            onDarkModeChange(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = ColorPrimary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.06f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // LANGUAGE

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = ColorSecondary.copy(alpha = 0.10f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Language,
                                contentDescription = null,
                                tint = ColorSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Language",
                            fontWeight = FontWeight.Bold,
                            color = ColorTertiary
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = if (language == "en")
                                "English"
                            else
                                "Kannada",
                            fontSize = 12.sp,
                            color = ColorTertiary.copy(alpha = 0.55f)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ColorSecondary.copy(alpha = 0.10f),
                        modifier = Modifier
                            .height(40.dp)
                            .wrapContentWidth()
                    ) {

                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = if (language == "en") "EN" else "ಕನ್ನಡ",
                                color = ColorSecondary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 6.dp)
                            )

                            Switch(
                                checked = language == "kn",
                                onCheckedChange = {

                                    onLanguageChange(
                                        if (it) "kn" else "en"
                                    )
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = ColorSecondary
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    color = ColorTertiary.copy(alpha = 0.06f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // NOTIFICATIONS

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color(0xFF8B5CF6).copy(alpha = 0.10f),
                        modifier = Modifier.size(52.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = null,
                                tint = Color(0xFF8B5CF6)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Notifications",
                            fontWeight = FontWeight.Bold,
                            color = ColorTertiary
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Service alerts and updates",
                            fontSize = 12.sp,
                            color = ColorTertiary.copy(alpha = 0.55f)
                        )
                    }

                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF8B5CF6)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF1F2)
            ),
            border = BorderStroke(
                1.dp,
                Color.Red.copy(alpha = 0.08f)
            )
        ) {

            Row(
                modifier = Modifier.padding(22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.Red.copy(alpha = 0.08f),
                    modifier = Modifier.size(52.dp)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = "Sign Out",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Logout from Mane Kelsa",
                        fontSize = 12.sp,
                        color = Color.Red.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}