package com.example.model

data class TwelveGua(
    val name: String,           // 卦名 (如 泰卦)
    val symbol: String,         // 卦符 (如 ䷊)
    val upperTrigram: String,   // 上卦 (坤为地)
    val lowerTrigram: String,   // 下卦 (乾为天)
    val explanation: String,    // 十二辟卦月令气象释义
    val auspiciousNote: String  // 本命辟卦总评
)

data class TiebanCalculationResult(
    val subjectName: String,
    val gender: Gender,
    val birthSolarDate: String,
    val birthLunarDate: String,
    val baZiChart: BaZiChart,
    val keDivision: KeDivision,
    val inquiryDate: String,
    
    // 铁板神数核心数理
    val innateFateNumber: Int,         // 先天命数
    val fiveTonesNumber: Int,          // 五音命数
    val fiveTonesLabel: String,        // 五音类别 (角、徵、宫、商、羽)
    val dayFateNumber: Int,            // 日命数
    val hourFortuneNumber: Int,        // 时运数
    val baseDestinyNumber: Int,        // 本命数
    val twelveGua: TwelveGua,          // 出生月令对应十二辟卦
    val kaoKeSummary: String,          // 考刻验证结果说明
    val generalDestinyPoem: String,    // 本命元神断诗
    val generalDestinySummary: String, // 本命总断语
    
    // 1-100岁流年原/修正双栏断语清单
    val yearlyVerses: List<YearlyVerse>
)
