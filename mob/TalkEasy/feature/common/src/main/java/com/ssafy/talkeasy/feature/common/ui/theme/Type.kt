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

val typography =
    Typography(
        titleLarge = TextStyle(
            fontFamily = welcomeFont,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
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

val textStyleBold90 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 90.sp,
    lineHeight = 64.sp,
    letterSpacing = 0.5.sp
)

val textStyleBold40 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 40.sp,
    lineHeight = 44.sp,
    letterSpacing = 0.5.sp
)

val textStyleBold36 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 36.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleBold32 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleBold30 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 30.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleBold24 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleBold22 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleNormal30 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Normal,
    fontSize = 30.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val textStyleNormal28 = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Normal,
    fontSize = 28.sp,
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

val textStyleNormal14Vertical = TextStyle(
    fontFamily = welcomeFont,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 16.sp,
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