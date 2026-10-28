package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AgeRangeFilter
import com.example.ui.TiebanViewModel
import com.example.ui.components.ChineseSeal
import com.example.ui.components.DualVerseCard
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.BronzeGoldBg
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.InkLight
import com.example.ui.theme.SealRed
import com.example.ui.theme.SealRedBg
import com.example.ui.theme.XuanPaper
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperCard
import com.example.ui.theme.XuanPaperDark
import kotlinx.coroutines.launch

@Composable
fun VersesScreen(
    viewModel: TiebanViewModel,
    modifier: Modifier = Modifier
) {
    val result by viewModel.calculationResult.collectAsState()
    val searchQuery by viewModel.verseSearchQuery.collectAsState()
    val ageFilter by viewModel.ageFilter.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    if (result == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "请先在前页排盘计算以生成流年断语",
                color = InkLight,
                fontSize = 14.sp
            )
        }
        return
    }

    val res = result!!

    // 过滤逻辑
    val filteredVerses = res.yearlyVerses.filter { verse ->
        val inAgeRange = verse.age in ageFilter.startAge..ageFilter.endAge
        val matchesSearch = if (searchQuery.isBlank()) true else {
            val q = searchQuery.trim()
            "${verse.age}".contains(q) ||
                    verse.ganZhi.contains(q) ||
                    verse.aspect.contains(q) ||
                    verse.originalVerse.contains(q) ||
                    verse.originalJudgment.contains(q) ||
                    verse.correctedVerse.contains(q) ||
                    verse.correctedJudgment.contains(q) ||
                    "${verse.verseCode}".contains(q)
        }
        inAgeRange && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(XuanPaper)
            .testTag("verses_screen_root")
    ) {
        // 顶部检索与年龄分类过滤区
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(XuanPaperCard)
                .border(1.dp, XuanPaperBorder)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "1 至 100 岁原/修正断语对照",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkBlack,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    ChineseSeal(text = "对照", isSmall = true)
                }

                Text(
                    text = "共检索到 ${filteredVerses.size} 岁流年",
                    fontSize = 12.sp,
                    color = BronzeGold,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 搜索输入框
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setVerseSearchQuery(it) },
                placeholder = { Text("搜索年龄、干支、原诗句、修正释义或条文数...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = InkGrey) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setVerseSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "清除", tint = InkGrey)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_search_verses"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SealRed,
                    unfocusedBorderColor = XuanPaperBorder,
                    focusedLabelColor = SealRed
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 年龄段滚动过滤标签
            val filterScrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(filterScrollState),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AgeRangeFilter.values().forEach { filter ->
                    val isSelected = ageFilter == filter
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSelected) SealRed else XuanPaperDark,
                                RoundedCornerShape(16.dp)
                            )
                            .border(
                                1.dp,
                                if (isSelected) SealRed else XuanPaperBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                viewModel.setAgeFilter(filter)
                                scope.launch { listState.scrollToItem(0) }
                            }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .testTag("filter_chip_${filter.name}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter.label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else InkGrey
                        )
                    }
                }
            }
        }

        // 双栏条文卡片列表
        if (filteredVerses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FilterAlt,
                        contentDescription = null,
                        modifier = Modifier.padding(bottom = 8.dp),
                        tint = InkLight
                    )
                    Text(
                        text = "未查询到符合条件的流年条文",
                        color = InkGrey,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "请尝试清空搜索词或切换年龄分期",
                        color = InkLight,
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(filteredVerses, key = { it.age }) { verse ->
                    DualVerseCard(verse = verse)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
