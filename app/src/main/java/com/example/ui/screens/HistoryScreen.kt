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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.TiebanRecord
import com.example.ui.TiebanViewModel
import com.example.ui.components.BronzeTag
import com.example.ui.components.ChineseSeal
import com.example.ui.components.ClassicalCard
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: TiebanViewModel,
    modifier: Modifier = Modifier
) {
    val records by viewModel.savedRecords.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(XuanPaper)
            .padding(16.dp)
            .testTag("history_screen_root")
    ) {
        ClassicalCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "古籍书匣 · 命盘档案",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkBlack,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "已收藏 ${records.size} 位缘主排盘记录",
                        fontSize = 12.sp,
                        color = InkGrey
                    )
                }
                ChineseSeal(text = "书匣")
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (records.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FolderOpen,
                        contentDescription = null,
                        modifier = Modifier.padding(bottom = 12.dp),
                        tint = BronzeGold
                    )
                    Text(
                        text = "书匣内尚无保存的命盘档案",
                        fontSize = 15.sp,
                        color = InkGrey,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "您可以在【排盘起数】或【核心数理】页面点击【归匣保存】",
                        fontSize = 12.sp,
                        color = InkLight
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(records, key = { it.id }) { record ->
                    HistoryRecordCard(
                        record = record,
                        onLoad = { viewModel.loadFromRecord(record) },
                        onDelete = { viewModel.deleteRecord(record) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryRecordCard(
    record: TiebanRecord,
    onLoad: () -> Unit,
    onDelete: () -> Unit
) {
    val dateStr = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA).format(Date(record.createdAt))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(XuanPaperCard, RoundedCornerShape(8.dp))
            .border(1.2.dp, XuanPaperBorder, RoundedCornerShape(8.dp))
            .padding(12.dp)
            .testTag("history_record_${record.id}")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = record.subjectName,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = InkBlack,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    ChineseSeal(text = record.gender, isSmall = true)
                    Spacer(modifier = Modifier.width(6.dp))
                    BronzeTag(text = record.twelveGuaName)
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("btn_delete_record_${record.id}")
                ) {
                    Icon(
                        Icons.Default.DeleteOutline,
                        contentDescription = "删除档案",
                        tint = InkLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 八字四柱
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(XuanPaperDark, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "四柱：${record.baziString}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkBlack,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "先天数: ${record.innateFateNumber} · 本命数: ${record.baseDestinyNumber}",
                    fontSize = 11.sp,
                    color = BronzeGold,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = dateStr,
                    fontSize = 11.sp,
                    color = InkLight
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onLoad,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .testTag("btn_load_record_${record.id}"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SealRed,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(Icons.Default.HistoryEdu, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("回溯载入命盘推算", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
