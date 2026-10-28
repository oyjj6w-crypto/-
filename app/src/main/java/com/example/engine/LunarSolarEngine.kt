package com.example.engine

import com.example.model.BaZiChart
import com.example.model.Element
import com.example.model.KeDivision
import com.example.model.Pillar
import java.util.Calendar

object LunarSolarEngine {

    val HEAVENLY_STEMS = listOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
    val EARTHLY_BRANCHES = listOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")
    val ZODIACS = listOf("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪")

    private val NA_YIN_MAP = mapOf(
        "甲子" to "海中金", "乙丑" to "海中金", "丙寅" to "炉中火", "丁卯" to "炉中火",
        "戊辰" to "大林木", "己巳" to "大林木", "庚午" to "路旁土", "辛未" to "路旁土",
        "壬申" to "剑锋金", "癸酉" to "剑锋金", "甲戌" to "山头火", "乙亥" to "山头火",
        "丙子" to "涧下水", "丁丑" to "涧下水", "戊寅" to "城头土", "己卯" to "城头土",
        "庚辰" to "白蜡金", "辛巳" to "白蜡金", "壬午" to "杨柳木", "癸未" to "杨柳木",
        "甲申" to "泉中水", "乙酉" to "泉中水", "丙戌" to "屋上土", "丁亥" to "屋上土",
        "戊子" to "霹雳火", "己丑" to "霹雳火", "庚寅" to "松柏木", "辛卯" to "松柏木",
        "壬辰" to "长流水", "癸巳" to "长流水", "甲午" to "砂中金", "乙未" to "砂中金",
        "丙申" to "山下火", "丁酉" to "山下火", "戊戌" to "平地木", "己亥" to "平地木",
        "庚子" to "壁上土", "辛丑" to "壁上土", "壬寅" to "金箔金", "癸卯" to "金箔金",
        "甲辰" to "覆灯火", "乙巳" to "覆灯火", "丙午" to "天河水", "丁未" to "天河水",
        "戊申" to "大驿土", "己酉" to "大驿土", "庚戌" to "钗钏金", "辛亥" to "钗钏金",
        "壬子" to "桑柘木", "癸丑" to "桑柘木", "甲寅" to "大溪水", "乙卯" to "大溪水",
        "丙辰" to "沙中土", "丁巳" to "沙中土", "戊午" to "天上火", "己未" to "天上火",
        "庚申" to "石榴木", "辛酉" to "石榴木", "壬戌" to "大海水", "癸亥" to "大海水"
    )

    private val SOLAR_TERMS = listOf(
        "小寒", "大寒", "立春", "雨水", "惊蛰", "春分",
        "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
        "小暑", "大暑", "立秋", "处暑", "白露", "秋分",
        "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
    )

    // 太玄配数
    fun getTaiXuanNumber(stemOrBranch: String): Int {
        return when (stemOrBranch) {
            "甲", "己", "子", "午" -> 9
            "乙", "庚", "丑", "未" -> 8
            "丙", "辛", "寅", "申" -> 7
            "丁", "壬", "卯", "酉" -> 6
            "戊", "癸", "辰", "戌" -> 5
            "巳", "亥" -> 4
            else -> 5
        }
    }

    fun getElement(stem: String): Element {
        return when (stem) {
            "甲", "乙" -> Element.WOOD
            "丙", "丁" -> Element.FIRE
            "戊", "己" -> Element.EARTH
            "庚", "辛" -> Element.GOLD
            "壬", "癸" -> Element.WATER
            else -> Element.EARTH
        }
    }

    fun getBranchElement(branch: String): Element {
        return when (branch) {
            "寅", "卯" -> Element.WOOD
            "巳", "午" -> Element.FIRE
            "辰", "戌", "丑", "未" -> Element.EARTH
            "申", "酉" -> Element.GOLD
            "亥", "子" -> Element.WATER
            else -> Element.EARTH
        }
    }

    fun calculateChart(year: Int, month: Int, day: Int, hour: Int, minute: Int): BaZiChart {
        // 1. 年柱 (立春为界，简化基准以公历2月4日为交接节气)
        val isBeforeLiChun = (month < 2) || (month == 2 && day < 4)
        val adjustedYear = if (isBeforeLiChun) year - 1 else year
        val yearOffset = (adjustedYear - 4) % 60
        val posYear = if (yearOffset >= 0) yearOffset else yearOffset + 60
        val yearStem = HEAVENLY_STEMS[posYear % 10]
        val yearBranch = EARTHLY_BRANCHES[posYear % 12]
        val yearNaYin = NA_YIN_MAP["$yearStem$yearBranch"] ?: "海中金"
        val yearPillar = Pillar(yearStem, yearBranch, yearNaYin, getElement(yearStem))

        // 2. 月柱 (五虎遁年起月)
        // 甲己之年丙作首，乙庚之岁戊为头，丙辛之岁寻庚上，丁壬壬寅顺行求，若问戊癸何方发，甲寅之上好追求
        val yearStemIndex = HEAVENLY_STEMS.indexOf(yearStem)
        val startMonthStemIndex = when (yearStemIndex % 5) {
            0 -> 2 // 丙
            1 -> 4 // 戊
            2 -> 6 // 庚
            3 -> 8 // 壬
            else -> 0 // 甲
        }
        // 月支 (寅月为正月)
        val monthBranchIndex = when {
            (month == 2 && day >= 4) || (month == 3 && day < 5) -> 2 // 寅
            (month == 3 && day >= 5) || (month == 4 && day < 5) -> 3 // 卯
            (month == 4 && day >= 5) || (month == 5 && day < 5) -> 4 // 辰
            (month == 5 && day >= 5) || (month == 6 && day < 5) -> 5 // 巳
            (month == 6 && day >= 5) || (month == 7 && day < 7) -> 6 // 午
            (month == 7 && day >= 7) || (month == 8 && day < 7) -> 7 // 未
            (month == 8 && day >= 7) || (month == 9 && day < 7) -> 8 // 申
            (month == 9 && day >= 7) || (month == 10 && day < 8) -> 9 // 酉
            (month == 10 && day >= 8) || (month == 11 && day < 7) -> 10 // 戌
            (month == 11 && day >= 7) || (month == 12 && day < 7) -> 11 // 亥
            (month == 12 && day >= 7) || (month == 1 && day < 5) -> 0 // 子
            else -> 1 // 丑
        }
        val monthOffsetFromYin = (monthBranchIndex - 2 + 12) % 12
        val monthStem = HEAVENLY_STEMS[(startMonthStemIndex + monthOffsetFromYin) % 10]
        val monthBranch = EARTHLY_BRANCHES[monthBranchIndex]
        val monthNaYin = NA_YIN_MAP["$monthStem$monthBranch"] ?: "大林木"
        val monthPillar = Pillar(monthStem, monthBranch, monthNaYin, getElement(monthStem))

        // 3. 日柱 (高精度儒略日推算干支)
        val julianDay = calculateJulianDay(year, month, day)
        val dayJiaZiIndex = ((julianDay + 49) % 60).toInt()
        val posDayIndex = if (dayJiaZiIndex >= 0) dayJiaZiIndex else dayJiaZiIndex + 60
        val dayStem = HEAVENLY_STEMS[posDayIndex % 10]
        val dayBranch = EARTHLY_BRANCHES[posDayIndex % 12]
        val dayNaYin = NA_YIN_MAP["$dayStem$dayBranch"] ?: "炉中火"
        val dayPillar = Pillar(dayStem, dayBranch, dayNaYin, getElement(dayStem))

        // 4. 时柱 (五鼠遁日起时)
        // 甲己还加甲，乙庚丙作初，丙辛从戊起，丁壬庚子居，戊癸何方发，壬子是真途
        val hourBranchIndex = when (hour) {
            23, 0 -> 0   // 子
            1, 2 -> 1    // 丑
            3, 4 -> 2    // 寅
            5, 6 -> 3    // 卯
            7, 8 -> 4    // 辰
            9, 10 -> 5   // 巳
            11, 12 -> 6  // 午
            13, 14 -> 7  // 未
            15, 16 -> 8  // 申
            17, 18 -> 9  // 酉
            19, 20 -> 10 // 戌
            21, 22 -> 11 // 亥
            else -> 0
        }
        val dayStemIndex = HEAVENLY_STEMS.indexOf(dayStem)
        val startHourStemIndex = when (dayStemIndex % 5) {
            0 -> 0 // 甲
            1 -> 2 // 丙
            2 -> 4 // 戊
            3 -> 6 // 庚
            else -> 8 // 壬
        }
        val hourStem = HEAVENLY_STEMS[(startHourStemIndex + hourBranchIndex) % 10]
        val hourBranch = EARTHLY_BRANCHES[hourBranchIndex]
        val hourNaYin = NA_YIN_MAP["$hourStem$hourBranch"] ?: "剑锋金"
        val hourPillar = Pillar(hourStem, hourBranch, hourNaYin, getElement(hourStem))

        val solarDateStr = String.format("%04d年%02d月%02d日 %02d:%02d", year, month, day, hour, minute)
        val (lunarYear, lunarMonth, lunarDay, isLeap) = solarToLunar(year, month, day)
        val leapText = if (isLeap) "闰" else ""
        val lunarDateStr = "${yearPillar.fullText}年 $leapText${formatChineseMonth(lunarMonth)}月${formatChineseDay(lunarDay)} ${hourBranch}时"
        val solarTerm = getApproximateSolarTerm(month, day)

        return BaZiChart(
            yearPillar = yearPillar,
            monthPillar = monthPillar,
            dayPillar = dayPillar,
            hourPillar = hourPillar,
            solarDateString = solarDateStr,
            lunarDateString = lunarDateStr,
            solarTerm = solarTerm
        )
    }

    private fun calculateJulianDay(year: Int, month: Int, day: Int): Long {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = y / 100
        val b = 2 - a + a / 4
        return (365.25 * (y + 4716)).toLong() + (30.6001 * (m + 1)).toLong() + day + b - 1524
    }

    // 农历近似换算算法，便于直观展示
    fun solarToLunar(year: Int, month: Int, day: Int): Quadruple<Int, Int, Int, Boolean> {
        val cal = Calendar.getInstance()
        cal.set(year, month - 1, day)
        // 农历年相较公历通常在正月初一前后，这里计算农历对应大致月日
        val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)
        val springFestivalDayOfYear = 35 // 约2月4日前后
        val approxLunarYear = if (dayOfYear < springFestivalDayOfYear) year - 1 else year
        val daysFromSpring = if (dayOfYear >= springFestivalDayOfYear) {
            dayOfYear - springFestivalDayOfYear
        } else {
            365 + dayOfYear - springFestivalDayOfYear
        }
        val approxMonth = (daysFromSpring / 29.53).toInt() + 1
        val safeMonth = ((approxMonth - 1) % 12) + 1
        val approxDay = (daysFromSpring % 29.53).toInt() + 1
        val safeDay = ((approxDay - 1) % 30) + 1
        return Quadruple(approxLunarYear, safeMonth, safeDay, false)
    }

    private fun formatChineseMonth(m: Int): String {
        return when (m) {
            1 -> "正"
            2 -> "二"
            3 -> "三"
            4 -> "四"
            5 -> "五"
            6 -> "六"
            7 -> "七"
            8 -> "八"
            9 -> "九"
            10 -> "十"
            11 -> "冬"
            12 -> "腊"
            else -> "$m"
        }
    }

    private fun formatChineseDay(d: Int): String {
        val tens = when (d / 10) {
            0 -> "初"
            1 -> if (d == 10) "初" else "十"
            2 -> if (d == 20) "二" else "廿"
            3 -> "三"
            else -> "初"
        }
        val ones = when (d % 10) {
            1 -> "一"
            2 -> "二"
            3 -> "三"
            4 -> "四"
            5 -> "五"
            6 -> "六"
            7 -> "七"
            8 -> "八"
            9 -> "九"
            0 -> "十"
            else -> "一"
        }
        return "$tens$ones"
    }

    private fun getApproximateSolarTerm(month: Int, day: Int): String {
        return when (month) {
            1 -> if (day < 20) "小寒" else "大寒"
            2 -> if (day < 19) "立春" else "雨水"
            3 -> if (day < 21) "惊蛰" else "春分"
            4 -> if (day < 20) "清明" else "谷雨"
            5 -> if (day < 21) "立夏" else "小满"
            6 -> if (day < 21) "芒种" else "夏至"
            7 -> if (day < 23) "小暑" else "大暑"
            8 -> if (day < 23) "立秋" else "处暑"
            9 -> if (day < 23) "白露" else "秋分"
            10 -> if (day < 23) "寒露" else "霜降"
            11 -> if (day < 22) "立冬" else "小雪"
            12 -> if (day < 22) "大雪" else "冬至"
            else -> "中气"
        }
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
