package com.example.localservice.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localservice.i18n.t

private val Orange = Color(0xFFFF5A1F)

@Composable
fun WorkerAccountDropdown(
    language: String,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onOpenIDCard: () -> Unit,
    onOpenEarnings: () -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit
) {

    val tr: (String) -> String = {
        t(language, it)
    }
    Column(
        modifier = Modifier
            .background(
                Color.White,
                RoundedCornerShape(28.dp)
            )
            .width(280.dp)
    )
    {

        DropdownMenuItem(
            text = {

                Text(
                    text = tr("myIdCard"),
                    fontSize = 20.sp
                )
            },
            leadingIcon = {

                Icon(
                    Icons.Outlined.Badge,
                    contentDescription = null,
                    tint = Orange
                )
            },
            onClick = onOpenIDCard
        )

        DropdownMenuItem(
            text = {

                Text(
                    text = tr("earningsHistory"),
                    fontSize = 20.sp
                )
            },
            leadingIcon = {

                Icon(
                    Icons.Outlined.History,
                    contentDescription = null,
                    tint = Orange
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = Color(0xFF4B4B45)
            ),
            onClick = onOpenEarnings
        )

        DropdownMenuItem(
            text = {

                Text(
                    text = tr("accountSettings"),
                    fontSize = 20.sp
                )
            },
            leadingIcon = {

                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            onClick = onOpenSettings
        )

        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 10.dp
            ),
            color = Color(0xFFEAEAEA)
        )

        DropdownMenuItem(
            text = {

                Text(
                    text = tr("logout"),
                    fontSize = 20.sp,
                    color = Color.Red
                )
            },
            leadingIcon = {

                Icon(
                    Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null,
                    tint = Color.Red
                )
            },
            onClick = onLogout
        )
    }
}