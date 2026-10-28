package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TwelveGua
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.InkLight
import com.example.ui.theme.SealRed
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperCard
import com.example.ui.theme.XuanPaperDark

@Composable
fun GuaCard(
    gua: TwelveGua,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(XuanPaperCard, RoundedCornerShape(8.dp))
            .border(1.dp, XuanPaperBorder, RoundedCornerShape(8.dp))
            .padding(12.dp)
            .testTag("gua_card_${gua.name}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧卦象大符
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(XuanPaperDark, RoundedCornerShape(6.dp))
                    .border(1.dp, BronzeGold.copy(alpha = 0.4f), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gua.symbol,
                    fontSize = 42.sp,
                    color = InkBlack
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 右侧卦辞与释义
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "本命月令 · 【${gua.name}】",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SealRed,
                        fontFamily = FontFamily.Serif
                    )
                    ChineseSeal(text = "辟卦", isSmall = true)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = "${gua.upperTrigram} / ${gua.lowerTrigram}",
                        fontSize = 11.sp,
                        color = BronzeGold,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = gua.explanation,
                    fontSize = 12.sp,
                    color = InkGrey,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "【象曰】${gua.auspiciousNote}",
                    fontSize = 11.sp,
                    color = InkLight,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}
