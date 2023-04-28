package com.ssafy.talkeasy.feature.common.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.talkeasy.feature.common.R

val welcomeFont =
    FontFamily(
        Font(R.font.welcome_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.welcome_bold, FontWeight.Bold, FontStyle.Normal)
    )

val Typography =
    Typography(
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleSmall = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodySmall = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        )
    )

val textStyleBold22 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleNormal22 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleNormal16 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val shapes =
    Shapes(
        extraSmall = RoundedCornerShape(5.dp),
        small = RoundedCornerShape(10.dp),
        medium = RoundedCornerShape(15.dp),
        large = RoundedCornerShape(20.dp),
        extraLarge = RoundedCornerShape(150.dp)
    )