package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FortuneLevel
import com.example.model.YearlyVerse
import com.example.ui.theme.BambooGreen
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.BronzeGoldBg
import com.example.ui.theme.DarkCyan
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.InkLight
import com.example.ui.theme.SealRed
import com.example.ui.theme.SealRedBg
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperCard
import com.example.ui.theme.XuanPaperDark

@Composable
fun DualVerseCard(
    verse: YearlyVerse,
    modifier: Modifier = Modifier
) {
    val fortuneColor = when (verse.fortuneLevel) {
        FortuneLevel.GREAT_LUCK -> SealRed
        FortuneLevel.GOOD_LUCK -> BronzeGold
        FortuneLevel.MODERATE -> DarkCyan
        FortuneLevel.CHANGE -> BambooGreen
        FortuneLevel.CAUTION -> Color(0xFFC2592A)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(XuanPaperCard, RoundedCornerShape(8.dp))
            .border(1.5.dp, XuanPaperBorder, RoundedCornerShape(8.dp))
            .testTag("dual_verse_card_${verse.age}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // 顶部流年信息栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(SealRed, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${verse.age}岁",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${verse.year}年 · ${verse.ganZhi} (${verse.naYin})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = InkBlack,
                        fontFamily = FontFamily.Serif
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BronzeTag(text = verse.aspect)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .border(1.dp, fortuneColor, RoundedCornerShape(3.dp))
                            .background(fortuneColor.copy(alpha = 0.08f), RoundedCornerShape(3.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = verse.fortuneLevel.label,
                            color = fortuneColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 数理条文编号与推导标注
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(XuanPaperDark, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "条文数【${verse.verseCode}】· ${verse.toneMarker}",
                    fontSize = 11.sp,
                    color = BronzeGold,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = verse.formula,
                    fontSize = 10.sp,
                    color = InkLight
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 核心双栏对照视图 (并列显示原断语与校正断语)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 左栏：【古籍原条文 / 原断语】
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(SealRedBg.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                        .border(1.dp, SealRed.copy(alpha = 0.25f), RoundedCornerShape(6.dp))
                        .padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(12.dp)
                                .background(SealRed, RoundedCornerShape(1.dp))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "古籍原条文",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SealRed
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = verse.originalVerse,
                        fontSize = 12.sp,
                        color = InkBlack,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "【原断】${verse.originalJudgment}",
                            fontSize = 10.sp,
                            color = InkGrey,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // 右栏：【考订校正断语 / 现代详析】
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(BronzeGoldBg.copy(alpha = 0.7f), RoundedCornerShape(6.dp))
                        .border(1.dp, BronzeGold.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
                        .padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(12.dp)
                                .background(BronzeGold, RoundedCornerShape(1.dp))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "校正断语详析",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BronzeGold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = verse.correctedVerse,
                        fontSize = 12.sp,
                        color = InkBlack,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(4.dp))
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "【详释】${verse.correctedJudgment}",
                            fontSize = 10.sp,
                            color = InkGrey,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}
