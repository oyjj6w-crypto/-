package com.example.model

data class YearlyVerse(
    val age: Int,                     // 岁数 (1~100)
    val year: Int,                    // 对应公元公历年份
    val ganZhi: String,               // 流年干支 (如 壬寅)
    val naYin: String,                // 纳音五行 (如 金箔金)
    val verseCode: Int,               // 条文条目编号 (铁板神数神机数，如 3841)
    val toneMarker: String,           // 四声标记与母音 (如 宫平甲一)
    val formula: String,              // 推导算法公式说明
    val originalVerse: String,        // 原条文 (古风七言/五言诗句)
    val originalJudgment: String,     // 原断语 (古籍原始批断)
    val correctedVerse: String,       // 校正条文 (考订后修正条文)
    val correctedJudgment: String,    // 校正断语 (当代白话精义详批)
    val fortuneLevel: FortuneLevel,   // 吉凶定级
    val aspect: String                // 关注领域 (如：学业起运、事业迁升、婚配子息、家业置产、修德守静)
)
