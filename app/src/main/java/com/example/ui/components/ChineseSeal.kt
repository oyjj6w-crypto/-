package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Element
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.BronzeGoldBg
import com.example.ui.theme.ElementEarth
import com.example.ui.theme.ElementFire
import com.example.ui.theme.ElementGold
import com.example.ui.theme.ElementWater
import com.example.ui.theme.ElementWood
import com.example.ui.theme.SealRed
import com.example.ui.theme.SealRedBg

@Composable
fun ChineseSeal(
    text: String,
    modifier: Modifier = Modifier,
    isSmall: Boolean = false,
    rotation: Float = -2f
) {
    Box(
        modifier = modifier
            .rotate(rotation)
            .border(
                width = if (isSmall) 1.5.dp else 2.5.dp,
                color = SealRed,
                shape = RoundedCornerShape(4.dp)
            )
            .background(SealRedBg, RoundedCornerShape(4.dp))
            .padding(
                horizontal = if (isSmall) 6.dp else 10.dp,
                vertical = if (isSmall) 3.dp else 6.dp
            )
            .testTag("chinese_seal_$text"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = SealRed,
            fontSize = if (isSmall) 11.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun ElementBadge(
    element: Element,
    modifier: Modifier = Modifier
) {
    val (color, bgColor) = when (element) {
        Element.GOLD -> ElementGold to Color(0xFFFBF4E4)
        Element.WOOD -> ElementWood to Color(0xFFE9F5ED)
        Element.WATER -> ElementWater to Color(0xFFE8F1F8)
        Element.FIRE -> ElementFire to Color(0xFFFDECEB)
        Element.EARTH -> ElementEarth to Color(0xFFF5EFE9)
    }

    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(3.dp))
            .border(1.dp, color.copy(alpha = 0.6f), RoundedCornerShape(3.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .testTag("element_badge_${element.name}"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${element.chinese}行",
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BronzeTag(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(BronzeGoldBg, RoundedCornerShape(3.dp))
            .border(1.dp, BronzeGold.copy(alpha = 0.5f), RoundedCornerShape(3.dp))
            .padding(horizontal = 7.dp, vertical = 2.5.dp)
            .testTag("bronze_tag_$text"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = BronzeGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
