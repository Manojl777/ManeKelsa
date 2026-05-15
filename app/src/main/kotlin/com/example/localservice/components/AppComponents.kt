package com.example.localservice.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.ui.*

/* ----------------------------------------------------------
   PREMIUM SERVICE CHIP
---------------------------------------------------------- */

@Composable
fun ServiceChip(
    text: String,
    icon: ImageVector,
    color: Color
) {
    Surface(
        shape = PillShape,
        color = color.copy(alpha = 0.12f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = text,
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}

/* ----------------------------------------------------------
   ONLINE STATUS DOT
---------------------------------------------------------- */

@Composable
fun LiveStatusDot(
    online: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = Modifier
            .size(10.dp)
            .background(
                if (online)
                    OnlineGreen.copy(alpha = alpha)
                else
                    OfflineRed.copy(alpha = alpha),
                CircleShape
            )
    )
}

/* ----------------------------------------------------------
   PREMIUM ACTION BUTTON
---------------------------------------------------------- */

@Composable
fun PremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = ColorPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(58.dp),
        shape = PillShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = MediumElevation
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

/* ----------------------------------------------------------
   VERIFIED BADGE
---------------------------------------------------------- */

@Composable
fun VerifiedBadge() {
    Surface(
        shape = CircleShape,
        color = OnlineGreen.copy(alpha = 0.15f)
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = OnlineGreen,
            modifier = Modifier
                .padding(6.dp)
                .size(16.dp)
        )
    }
}

/* ----------------------------------------------------------
   PREMIUM BACKGROUND BLOBS
---------------------------------------------------------- */

@Composable
fun PremiumBackgroundBlobs() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = (-40).dp, y = (-40).dp)
                .blur(80.dp)
                .background(
                    ColorPrimary.copy(alpha = 0.10f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 240.dp, y = 120.dp)
                .blur(80.dp)
                .background(
                    ColorSecondary.copy(alpha = 0.08f),
                    CircleShape
                )
        )
    }
}

/* ----------------------------------------------------------
   GLASS CARD
---------------------------------------------------------- */

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = modifier,
        shape = LargeCardShape,
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite
        ),
        border = BorderStroke(
            1.dp,
            SoftBorder
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = LowElevation
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content
        )
    }
}