package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.model.Gender
import com.example.model.KeDivision
import com.example.ui.CalendarType
import com.example.ui.TiebanViewModel
import com.example.ui.components.BaZiPillarsView
import com.example.ui.components.ChineseSeal
import com.example.ui.components.ClassicalCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.BronzeGoldBg
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.InkLight
import com.example.ui.theme.SealRed
import com.example.ui.theme.SealRedBg
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperDark

@Composable
fun PaiPanScreen(
    viewModel: TiebanViewModel,
    modifier: Modifier = Modifier
) {
    val subjectName by viewModel.subjectName.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val calendarType by viewModel.calendarType.collectAsState()
    val birthYear by viewModel.birthYear.collectAsState()
    val birthMonth by viewModel.birthMonth.collectAsState()
    val birthDay by viewModel.birthDay.collectAsState()
    val birthHour by viewModel.birthHour.collectAsState()
    val birthMinute by viewModel.birthMinute.collectAsState()
    val keDivision by viewModel.keDivision.collectAsState()
    val currentChart by viewModel.currentChart.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("paipan_screen_root")
    ) {
        // 顶部说明
        ClassicalCard {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "邵雍铁板神数 · 考刻起盘",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkBlack,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "以生辰四柱配太玄九数，推演先天命数与百岁流年",
                            fontSize = 11.sp,
                            color = InkLight
                        )
                    }
                    ChineseSeal(text = "神数")
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 缘主基本信息与干造坤造
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "缘主名讳与造化",
                    subtitle = "阳男阴女、阴男阳女行运顺逆自此而定"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = { viewModel.setSubjectName(it) },
                        label = { Text("求测缘主姓名/称谓") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_subject_name"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SealRed,
                            unfocusedBorderColor = XuanPaperBorder,
                            focusedLabelColor = SealRed
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 乾造 / 坤造 选择
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GenderSelector(
                        title = "乾造 (男命)",
                        isSelected = gender == Gender.MALE,
                        sealText = "乾",
                        onClick = { viewModel.setGender(Gender.MALE) },
                        modifier = Modifier.weight(1f)
                    )
                    GenderSelector(
                        title = "坤造 (女命)",
                        isSelected = gender == Gender.FEMALE,
                        sealText = "坤",
                        onClick = { viewModel.setGender(Gender.FEMALE) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 生辰与历法选择
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "生辰历法与时辰细度",
                    subtitle = "支持公农历双向转换与四柱八字即时对正"
                )

                // 历法切换 (公历 / 农历)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(XuanPaperDark, RoundedCornerShape(6.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalendarType.values().forEach { type ->
                        val isSelected = calendarType == type
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isSelected) SealRed else Color.Transparent,
                                    RoundedCornerShape(4.dp)
                                )
                                .clickable { viewModel.setCalendarType(type) }
                                .padding(vertical = 8.dp)
                                .testTag("calendar_type_${type.name}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type.label,
                                color = if (isSelected) Color.White else InkGrey,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 年月日调节组件
                DateAdjustRow(
                    label = "出生年份",
                    value = "${birthYear}年",
                    onDecrease = { viewModel.setBirthYear(birthYear - 1) },
                    onIncrease = { viewModel.setBirthYear(birthYear + 1) },
                    testTag = "adjust_year"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DateAdjustRow(
                        label = "月份",
                        value = "${birthMonth}月",
                        onDecrease = { viewModel.setBirthMonth(birthMonth - 1) },
                        onIncrease = { viewModel.setBirthMonth(birthMonth + 1) },
                        modifier = Modifier.weight(1f),
                        testTag = "adjust_month"
                    )
                    DateAdjustRow(
                        label = "日份",
                        value = "${birthDay}日",
                        onDecrease = { viewModel.setBirthDay(birthDay - 1) },
                        onIncrease = { viewModel.setBirthDay(birthDay + 1) },
                        modifier = Modifier.weight(1f),
                        testTag = "adjust_day"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 时分调节组件
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DateAdjustRow(
                        label = "时辰(小时)",
                        value = String.format("%02d时", birthHour),
                        onDecrease = { viewModel.setBirthHour(birthHour - 1) },
                        onIncrease = { viewModel.setBirthHour(birthHour + 1) },
                        modifier = Modifier.weight(1f),
                        testTag = "adjust_hour"
                    )
                    DateAdjustRow(
                        label = "刻分(分钟)",
                        value = String.format("%02d分", birthMinute),
                        onDecrease = { viewModel.setBirthMinute((birthMinute - 5 + 60) % 60) },
                        onIncrease = { viewModel.setBirthMinute((birthMinute + 5) % 60) },
                        modifier = Modifier.weight(1f),
                        testTag = "adjust_minute"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 考刻细化选择器 (铁板精髓：考刻定真机)
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "铁板考刻细度校准",
                    subtitle = "古云：刻分一差，天机迥异。以15分钟划为一刻",
                    sealTag = "考刻"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "当前时辰推选刻度：",
                        fontSize = 12.sp,
                        color = InkGrey
                    )
                    Text(
                        text = "${keDivision.label} (${keDivision.description})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BronzeGold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 八刻快速网格选择
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(KeDivision.CHU_1, KeDivision.CHU_2, KeDivision.CHU_3, KeDivision.CHU_4).forEach { ke ->
                            KeChip(
                                ke = ke,
                                isSelected = keDivision == ke,
                                onClick = { viewModel.setKeDivision(ke) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(KeDivision.ZHENG_1, KeDivision.ZHENG_2, KeDivision.ZHENG_3, KeDivision.ZHENG_4).forEach { ke ->
                            KeChip(
                                ke = ke,
                                isSelected = keDivision == ke,
                                onClick = { viewModel.setKeDivision(ke) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 即时四柱八字预览卡片
        ClassicalCard {
            Column {
                SectionHeader(
                    title = "八字四柱排盘即时映射",
                    subtitle = "公历：${currentChart.solarDateString}"
                )
                BaZiPillarsView(chart = currentChart)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 主操作推演按钮 (朱砂金印)
        Button(
            onClick = { viewModel.calculateAndNavigateToVerses() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_calculate_verses"),
            colors = ButtonDefaults.buttonColors(
                containerColor = SealRed,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "推演铁板神数 · 展开百岁双栏断语",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { viewModel.calculateAndNavigateToMetrics() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_view_metrics"),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = BronzeGold
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(BronzeGold)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "参研先天命数与十二辟卦",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun GenderSelector(
    title: String,
    isSelected: Boolean,
    sealText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                if (isSelected) SealRedBg else XuanPaperDark,
                RoundedCornerShape(6.dp)
            )
            .border(
                1.5.dp,
                if (isSelected) SealRed else XuanPaperBorder,
                RoundedCornerShape(6.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 12.dp)
            .testTag("gender_selector_$sealText"),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ChineseSeal(text = sealText, isSmall = true)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) SealRed else InkBlack
            )
        }
    }
}

@Composable
private fun DateAdjustRow(
    label: String,
    value: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Row(
        modifier = modifier
            .background(XuanPaperDark, RoundedCornerShape(6.dp))
            .border(1.dp, XuanPaperBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(start = 6.dp)) {
            Text(text = label, fontSize = 10.sp, color = InkLight)
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = InkBlack,
                fontFamily = FontFamily.Serif
            )
        }

        Row {
            IconButton(
                onClick = onDecrease,
                modifier = Modifier.padding(2.dp).testTag("${testTag}_minus")
            ) {
                Icon(Icons.Default.Remove, contentDescription = "减少", tint = InkGrey)
            }
            IconButton(
                onClick = onIncrease,
                modifier = Modifier.padding(2.dp).testTag("${testTag}_plus")
            ) {
                Icon(Icons.Default.Add, contentDescription = "增加", tint = InkGrey)
            }
        }
    }
}

@Composable
private fun KeChip(
    ke: KeDivision,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                if (isSelected) BronzeGoldBg else XuanPaperDark,
                RoundedCornerShape(4.dp)
            )
            .border(
                1.dp,
                if (isSelected) BronzeGold else XuanPaperBorder,
                RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = ke.label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) BronzeGold else InkGrey
        )
    }
}
