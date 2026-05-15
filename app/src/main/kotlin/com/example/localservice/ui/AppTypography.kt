package com.example.localservice.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.localservice.R

/* ----------------------------------------------------------
   FONT FAMILIES
---------------------------------------------------------- */

val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_regular, FontWeight.Normal),
    Font(R.font.plus_jakarta_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_bold, FontWeight.Bold),
    Font(R.font.plus_jakarta_extrabold, FontWeight.ExtraBold)
)

val Lexend = FontFamily(
    Font(R.font.lexend_regular, FontWeight.Normal),
    Font(R.font.lexend_medium, FontWeight.Medium),
    Font(R.font.lexend_semibold, FontWeight.SemiBold),
    Font(R.font.lexend_bold, FontWeight.Bold)
)

/* ----------------------------------------------------------
   TYPOGRAPHY SYSTEM
---------------------------------------------------------- */

val AppTypography = Typography(

    /* ------------------------------------------------------
       DISPLAY / BRAND HEADINGS
    ------------------------------------------------------ */

    displayLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
        lineHeight = 42.sp,
        letterSpacing = (-1).sp
    ),

    displayMedium = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.8).sp
    ),

    displaySmall = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.5).sp
    ),

    /* ------------------------------------------------------
       HEADINGS
    ------------------------------------------------------ */

    headlineLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    /* ------------------------------------------------------
       BODY TEXT
    ------------------------------------------------------ */

    bodyLarge = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),

    bodySmall = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),

    /* ------------------------------------------------------
       LABELS / BUTTONS / CHIPS
    ------------------------------------------------------ */

    labelLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),

    labelMedium = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),

    labelSmall = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        letterSpacing = 0.3.sp
    )
)