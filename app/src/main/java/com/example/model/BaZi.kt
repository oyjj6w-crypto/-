package com.example.model

enum class Gender(val label: String, val designation: String) {
    MALE("男 (乾造)", "乾造"),
    FEMALE("女 (坤造)", "坤造")
}

enum class Element(val label: String, val chinese: String) {
    GOLD("金", "金"),
    WOOD("木", "木"),
    WATER("水", "水"),
    FIRE("火", "火"),
    EARTH("土", "土")
}

data class Pillar(
    val stem: String,        // 天干
    val branch: String,      // 地支
    val naYin: String,       // 纳音 (如：海中金)
    val element: Element     // 五行主气
) {
    val fullText: String get() = "$stem$branch"
}

data class BaZiChart(
    val yearPillar: Pillar,
    val monthPillar: Pillar,
    val dayPillar: Pillar,
    val hourPillar: Pillar,
    val solarDateString: String,
    val lunarDateString: String,
    val solarTerm: String
)

enum class KeDivision(val code: Int, val label: String, val minutesOffset: Int, val description: String) {
    CHU_1(0, "初一刻", 0, "时初起生，气机初萌"),
    CHU_2(1, "初二刻", 15, "初刻渐盛，阴阳交泰"),
    CHU_3(2, "初三刻", 30, "初候正旺，清灵布野"),
    CHU_4(3, "初四刻", 45, "初刻向正，定数推移"),
    ZHENG_1(4, "正一刻", 60, "正刻交入，中气纯粹"),
    ZHENG_2(5, "正二刻", 75, "正刻中位，主气鼎盛"),
    ZHENG_3(6, "正三刻", 90, "正刻将迁，神机潜转"),
    ZHENG_4(7, "正四刻", 105, "正刻归末，气汇下辰")
}

enum class FortuneLevel(val label: String) {
    GREAT_LUCK("大吉·顺遂"),
    GOOD_LUCK("上吉·亨通"),
    MODERATE("中平·安稳"),
    CHANGE("迁动·转机"),
    CAUTION("谨慎·守静")
}
