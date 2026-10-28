package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkLight
import com.example.ui.theme.SealRed
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperCard

@Composable
fun ClassicalCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(XuanPaperCard, RoundedCornerShape(8.dp))
            .border(1.5.dp, XuanPaperBorder, RoundedCornerShape(8.dp))
            .padding(1.dp)
            .border(0.5.dp, BronzeGold.copy(alpha = 0.3f), RoundedCornerShape(7.dp))
            .padding(14.dp)
    ) {
        content()
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    sealTag: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().testTag("section_header_$title")) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(18.dp)
                    .background(SealRed, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = InkBlack,
                fontFamily = FontFamily.Serif
            )
            if (sealTag != null) {
                Spacer(modifier = Modifier.width(8.dp))
                ChineseSeal(text = sealTag, isSmall = true)
            }
        }
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = InkLight
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        ClassicalDivider()
    }
}

@Composable
fun ClassicalDivider(
    modifier: Modifier = Modifier,
    color: Color = XuanPaperBorder
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.weight(1f).height(1.dp)) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
        Canvas(modifier = Modifier.size(6.dp).padding(horizontal = 1.dp)) {
            drawCircle(color = BronzeGold.copy(alpha = 0.6f))
        }
        Canvas(modifier = Modifier.weight(1f).height(1.dp)) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}
