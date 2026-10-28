package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.TiebanViewModel
import com.example.ui.components.ChineseSeal
import com.example.ui.components.ClassicalCard
import com.example.ui.components.GuaCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.BronzeGoldBg
import com.example.ui.theme.DarkCyan
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.InkLight
import com.example.ui.theme.SealRed
import com.example.ui.theme.SealRedBg
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperDark

@Composable
fun FateMetricsScreen(
    viewModel: TiebanViewModel,
    modifier: Modifier = Modifier
) {
    val result by viewModel.calculationResult.collectAsState()
    val scrollState = rememberScrollState()

    if (result == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "请先在前页录入生辰以推演神数",
                color = InkLight,
                fontSize = 14.sp
            )
        }
        return
    }

    val res = result!!

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("fate_metrics_screen_root")
    ) {
        // 缘主题头宣纸卡片
        ClassicalCard {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = res.subjectName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = InkBlack,
                                fontFamily = FontFamily.Serif
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ChineseSeal(text = res.gender.designation, isSmall = true)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "生辰：${res.birthSolarDate} (${res.keDivision.label})",
                            fontSize = 12.sp,
                            color = InkGrey
                        )
                    }

                    Button(
                        onClick = { viewModel.saveCurrentChart() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SealRed,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.testTag("btn_save_chart")
                    ) {
                        Icon(Icons.Default.BookmarkAdd, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("归匣保存", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(XuanPaperDark, RoundedCornerShape(6.dp))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "年：${res.baZiChart.yearPillar.fullText}", fontWeight = FontWeight.Bold, color = InkBlack)
                    Text(text = "月：${res.baZiChart.monthPillar.fullText}", fontWeight = FontWeight.Bold, color = InkBlack)
                    Text(text = "日：${res.baZiChart.dayPillar.fullText}", fontWeight = FontWeight.Bold, color = InkBlack)
                    Text(text = "时：${res.baZiChart.hourPillar.fullText}", fontWeight = FontWeight.Bold, color = InkBlack)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 核心五大神数指标 (先天命数、五音命数、日命数、时运数、本命数)
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "铁板神数五大命理秘数",
                    subtitle = "太玄配数天地定位，演绎生平造化根基",
                    sealTag = "秘数"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricBox(
                        title = "先天命数",
                        number = "${res.innateFateNumber}",
                        description = "四柱干支与先天定数之根",
                        accentColor = SealRed,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "五音命数",
                        number = "${res.fiveTonesNumber}",
                        description = res.fiveTonesLabel,
                        accentColor = BronzeGold,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricBox(
                        title = "日命数",
                        number = "${res.dayFateNumber}",
                        description = "日元所临之命度节律",
                        accentColor = DarkCyan,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "时运数",
                        number = "${res.hourFortuneNumber}",
                        description = "时辰刻分之化机气运",
                        accentColor = BronzeGold,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 本命总神数
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SealRedBg, RoundedCornerShape(8.dp))
                        .border(1.5.dp, SealRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "本命数 (铁板归宗神机数)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = SealRed
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "以先天、日命、时运融汇考刻所得，统领百岁大运",
                                fontSize = 11.sp,
                                color = InkGrey
                            )
                        }

                        Text(
                            text = "${res.baseDestinyNumber}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SealRed,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 十二辟卦
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "出生月令十二辟卦",
                    subtitle = "易经十二消息卦，测度阴阳消长生息"
                )
                GuaCard(gua = res.twelveGua)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 考刻验证结果说明
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "考刻分度验断",
                    subtitle = "细分八刻考正父母寿元与生肖吉会"
                )
                Text(
                    text = res.kaoKeSummary,
                    fontSize = 13.sp,
                    color = InkBlack,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 本命元神断诗与总断语
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "本命元神总断批语",
                    subtitle = "邵雍传世数术总纲批示"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(XuanPaperDark, RoundedCornerShape(6.dp))
                        .border(1.dp, BronzeGold.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = res.generalDestinyPoem,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = InkBlack,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = res.generalDestinySummary,
                    fontSize = 13.sp,
                    color = InkGrey,
                    lineHeight = 19.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = { viewModel.selectTab(2) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_goto_verses_from_metrics"),
            colors = ButtonDefaults.buttonColors(
                containerColor = SealRed,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.MenuBook, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "查阅 1 至 100 岁原/修正双栏流年断语",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MetricBox(
    title: String,
    number: String,
    description: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(XuanPaperDark, RoundedCornerShape(6.dp))
            .border(1.dp, XuanPaperBorder, RoundedCornerShape(6.dp))
            .padding(10.dp)
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            color = InkLight
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = number,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = description,
            fontSize = 10.sp,
            color = InkGrey
        )
    }
}
