package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BaZiChart
import com.example.model.Pillar
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.InkLight
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperDark

@Composable
fun BaZiPillarsView(
    chart: BaZiChart,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("bazi_pillars_container")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PillarCard(title = "年柱", pillar = chart.yearPillar, modifier = Modifier.weight(1f))
            PillarCard(title = "月柱", pillar = chart.monthPillar, modifier = Modifier.weight(1f))
            PillarCard(title = "日柱", pillar = chart.dayPillar, modifier = Modifier.weight(1f))
            PillarCard(title = "时柱", pillar = chart.hourPillar, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(XuanPaperDark, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "农历：${chart.lunarDateString}",
                fontSize = 12.sp,
                color = InkGrey,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "节气：${chart.solarTerm}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = BronzeGold
            )
        }
    }
}

@Composable
private fun PillarCard(
    title: String,
    pillar: Pillar,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(XuanPaperDark, RoundedCornerShape(6.dp))
            .border(1.dp, XuanPaperBorder, RoundedCornerShape(6.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            color = InkLight,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(6.dp))

        // 天干
        Box(
            modifier = Modifier
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pillar.stem,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = InkBlack,
                fontFamily = FontFamily.Serif
            )
        }

        // 地支
        Box(
            modifier = Modifier
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pillar.branch,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = InkBlack,
                fontFamily = FontFamily.Serif
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        ElementBadge(element = pillar.element)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = pillar.naYin,
            fontSize = 10.sp,
            color = BronzeGold,
            fontWeight = FontWeight.Normal
        )
    }
}
