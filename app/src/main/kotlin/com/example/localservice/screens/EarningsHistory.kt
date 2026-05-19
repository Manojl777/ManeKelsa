package com.example.localservice.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.i18n.t

private val BgColor = Color(0xFFF7F4ED)
private val Orange = Color(0xFFFF5A1F)
private val Teal = Color(0xFF006D6F)

data class EarningItem(
    val amount: Int,
    val date: String,
    val time: String
)

@Composable
fun EarningsHistory(

    language: String,

    onLanguageChange: (String) -> Unit,

    darkMode: Boolean,

    onDarkModeChange: (Boolean) -> Unit,

    totalEarnings: Int,

    earningsList: List<EarningItem>,

    onBack: () -> Unit
) {

    val tr: (String) -> String = {
        t(language, it)
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
                    horizontal = 16.dp,
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
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text =
                    if (language == "kn")
                        "ಆದಾಯ ಇತಿಹಾಸ"
                    else
                        "Earnings History",

                color = Orange,

                fontWeight = FontWeight.ExtraBold,

                fontSize = 22.sp,

                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    onDarkModeChange(!darkMode)
                },

                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F6F6))
            ) {

                Icon(
                    if (darkMode)
                        Icons.Outlined.LightMode
                    else
                        Icons.Outlined.DarkMode,

                    contentDescription = null,

                    tint = Teal,

                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(50),

                color = Color(0xFFF4F7F7),

                border = BorderStroke(
                    1.dp,
                    Color(0xFFDDE5E5)
                ),

                onClick = {

                    if (language == "en") {
                        onLanguageChange("kn")
                    } else {
                        onLanguageChange("en")
                    }
                }
            ) {

                Text(
                    text =
                        if (language == "en")
                            "ಕನ್ನಡ"
                        else
                            "ENGLISH",

                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 9.dp
                    ),

                    fontWeight = FontWeight.Bold,

                    color = Teal,

                    fontSize = 11.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(20.dp),

            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {

                Surface(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(36.dp),

                    color = Orange,

                    shadowElevation = 10.dp
                ) {

                    Column(
                        modifier = Modifier.padding(
                            horizontal = 28.dp,
                            vertical = 26.dp
                        )
                    ) {

                        Text(
                            text =
                                if (language == "kn")
                                    "ಒಟ್ಟು ಆದಾಯ"
                                else
                                    "TOTAL EARNINGS",

                            color = Color(0xFFFFD8C8),

                            fontWeight = FontWeight.Bold,

                            letterSpacing = 4.sp,

                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text =
                                if (language == "kn")
                                    "₹${convertToKannadaNumbers(totalEarnings.toString())}"
                                else
                                    "₹$totalEarnings",

                            color = Color.White,

                            fontWeight = FontWeight.Black,

                            fontSize = 54.sp
                        )
                    }
                }
            }

            items(earningsList) { earning ->

                EarningHistoryCard(
                    earning = earning,
                    language = language
                )
            }
        }
    }
}

@Composable
private fun EarningHistoryCard(
    earning: EarningItem,
    language: String
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(32.dp),

        color = Color.White,

        shadowElevation = 4.dp
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 22.dp,
                vertical = 24.dp
            ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFF1EC)),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    Icons.Default.History,
                    contentDescription = null,
                    tint = Orange,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text =
                        if (language == "kn")
                            convertToKannadaNumbers(earning.date)
                        else
                            earning.date,

                    color = Color(0xFF4B4B45),

                    fontWeight = FontWeight.Bold,

                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text =
                        if (language == "kn")
                            convertToKannadaNumbers(earning.time)
                        else
                            earning.time,

                    color = Color.LightGray,

                    fontWeight = FontWeight.SemiBold,

                    fontSize = 15.sp
                )
            }

            Text(
                text =
                    if (language == "kn")
                        "₹${convertToKannadaNumbers(earning.amount.toString())}"
                    else
                        "₹${earning.amount}",

                color = Orange,

                fontWeight = FontWeight.Bold,

                fontSize = 28.sp
            )
        }
    }
}
