package com.example.engine

import com.example.model.BaZiChart
import com.example.model.Gender
import com.example.model.KeDivision
import com.example.model.TiebanCalculationResult
import com.example.model.TwelveGua
import com.example.model.YearlyVerse

object TiebanShenshuEngine {

    // 十二辟卦对照 (按农历出生月份与月令节气)
    val TWELVE_GUAS = listOf(
        TwelveGua("复卦", "䷗", "坤为地", "雷在地下", "十一月冬至一阳生，万物潜萌，生机初动。", "元亨利贞，出入无疾，朋来无咎。"),
        TwelveGua("临卦", "䷒", "坤为地", "泽在地上", "十二月大寒二阳渐长，敦临和顺，君子道长。", "元亨利贞，至于八月有凶，宜早图之。"),
        TwelveGua("泰卦", "䷊", "坤为地", "乾为天", "正月立春三阳开泰，天地交而万物通，安泰光大。", "小往大来，吉亨，无平不陂，无往不复。"),
        TwelveGua("大壮", "䷡", "震为雷", "乾为天", "二月惊蛰四阳鼎盛，雷在天上，刚健而止。", "利贞，君子以非礼弗履，刚健正大。"),
        TwelveGua("夬卦", "䷪", "兑为泽", "乾为天", "三月清明五阳决阴，泽上于天，去邪决乱。", "扬于王庭，孚号有厉，刚决柔也。"),
        TwelveGua("乾卦", "䷀", "乾为天", "乾为天", "四月立夏六阳纯干，群龙无首，纯阳至健。", "大哉乾元，万物资始，乃统天。刚健笃实。"),
        TwelveGua("姤卦", "䷫", "乾为天", "风在天下", "五月夏至一阴始生，天下有风，相遇交结。", "女壮勿用取女，天地相遇，品物咸章。"),
        TwelveGua("遁卦", "䷠", "乾为天", "山在天下", "六月大暑二阴浸长，天下有山，退避远害。", "亨小利贞，君子以远小人，不恶而严。"),
        TwelveGua("否卦", "䷋", "乾为天", "地在天上", "七月立秋三阴塞道，天地不交，否极泰来。", "拔茅茹以其汇，俭德辟难，不可荣以禄。"),
        TwelveGua("观卦", "䷓", "巽为风", "地在风下", "八月白露四阴上侵，风行地上，瞻仰省察。", "盥而不荐，有孚颙若，省方观民设教。"),
        TwelveGua("剥卦", "䷖", "艮为山", "地在山下", "九月寒露五阴剥阳，山附于地，硕果不食。", "不利有攸往，君子尚消息盈虚，天行也。"),
        TwelveGua("坤卦", "䷁", "坤为地", "坤为地", "十月立冬六阴纯粹，地势坤，厚德载物。", "含弘光大，品物咸亨，柔顺利贞，君子攸行。")
    )

    fun calculate(
        name: String,
        gender: Gender,
        chart: BaZiChart,
        ke: KeDivision,
        inquiryDate: String,
        birthYear: Int
    ): TiebanCalculationResult {
        // 1. 太玄配数计算
        val yStemNum = LunarSolarEngine.getTaiXuanNumber(chart.yearPillar.stem)
        val yBranchNum = LunarSolarEngine.getTaiXuanNumber(chart.yearPillar.branch)
        val mStemNum = LunarSolarEngine.getTaiXuanNumber(chart.monthPillar.stem)
        val mBranchNum = LunarSolarEngine.getTaiXuanNumber(chart.monthPillar.branch)
        val dStemNum = LunarSolarEngine.getTaiXuanNumber(chart.dayPillar.stem)
        val dBranchNum = LunarSolarEngine.getTaiXuanNumber(chart.dayPillar.branch)
        val hStemNum = LunarSolarEngine.getTaiXuanNumber(chart.hourPillar.stem)
        val hBranchNum = LunarSolarEngine.getTaiXuanNumber(chart.hourPillar.branch)

        // 2. 先天命数 (年柱干支数 + 月柱干支数 + 考刻偏置)
        val baseSum = (yStemNum + yBranchNum) * 10 + (mStemNum + mBranchNum)
        val innateFateNumber = ((baseSum + ke.code * 3) % 96) + 1

        // 3. 五音命数 (宫商角徵羽律数)
        val fiveTonesLabels = listOf("宫音(土)", "商音(金)", "角音(木)", "徵音(火)", "羽音(水)")
        val fiveToneIdx = (chart.yearPillar.stem.hashCode() + chart.dayPillar.branch.hashCode()).let {
            Math.abs(it) % 5
        }
        val fiveTonesLabel = fiveTonesLabels[fiveToneIdx]
        val fiveTonesNumber = ((dStemNum * 7 + hBranchNum * 9 + ke.code * 11) % 990) + 100

        // 4. 日命数与时运数
        val dayFateNumber = ((dStemNum * 9 + dBranchNum * 6) % 80) + 12
        val hourFortuneNumber = ((hStemNum * 10 + hBranchNum * 4) + ke.code * 17) % 640 + 64

        // 5. 本命数 (铁板定轨核心神数，通常在 1000 ~ 12000 之间)
        val baseDestinyNumber = (innateFateNumber * 108 + dayFateNumber * 37 + hourFortuneNumber + ke.code * 144) % 11999 + 1

        // 6. 出生月份对应辟卦
        val monthBranchIdx = LunarSolarEngine.EARTHLY_BRANCHES.indexOf(chart.monthPillar.branch).let {
            if (it < 0) 0 else it
        }
        // 地支：子(0)->十一月复，丑(1)->十二月临，寅(2)->正月泰，卯(3)->二月大壮...
        val guaIndex = when (monthBranchIdx) {
            0 -> 0  // 复
            1 -> 1  // 临
            2 -> 2  // 泰
            3 -> 3  // 大壮
            4 -> 4  // 夬
            5 -> 5  // 乾
            6 -> 6  // 姤
            7 -> 7  // 遁
            8 -> 8  // 否
            9 -> 9  // 观
            10 -> 10 // 剥
            11 -> 11 // 坤
            else -> 2
        }
        val twelveGua = TWELVE_GUAS[guaIndex]

        // 7. 考刻说明
        val kaoKeSummary = buildString {
            append("生逢${chart.hourPillar.branch}时${ke.label}。")
            if (gender == Gender.MALE) {
                append("乾造阳气渐升，当得${ke.description}。考刻真确，数由刻定。")
            } else {
                append("坤造柔顺含章，合应${ke.description}。阴阳应和，考刻归宗。")
            }
        }

        // 8. 本命总断语与诗
        val generalDestinyPoem = "命带${chart.yearPillar.naYin}基业长，行逢${twelveGua.name}气自昂。\n" +
                "先天定数${innateFateNumber}分明指，日命${dayFateNumber}数展锋芒。"
        val generalDestinySummary = "元命生合${chart.yearPillar.fullText}年${chart.monthPillar.fullText}月${chart.dayPillar.fullText}日${chart.hourPillar.fullText}时，" +
                "值${twelveGua.name}月令，秉${fiveTonesLabel}之精。生平刚毅有为，谋事虽历经千虑，然中晚岁运势开泰，可承重任、树家声。"

        // 9. 1-100岁流年条文与原/修正双栏对照
        val yearlyVerses = mutableListOf<YearlyVerse>()
        val startYear = birthYear
        for (age in 1..100) {
            val currYear = startYear + age - 1
            val yearOffset = (currYear - 4) % 60
            val posYear = if (yearOffset >= 0) yearOffset else yearOffset + 60
            val ganZhi = "${LunarSolarEngine.HEAVENLY_STEMS[posYear % 10]}${LunarSolarEngine.EARTHLY_BRANCHES[posYear % 12]}"
            val naYin = chart.yearPillar.naYin

            // 计算条文编号与推导公式
            val rawFormulaCode = (baseDestinyNumber + age * 113 + ke.code * 29 + posYear * 7) % 11999 + 1
            val formulaText = "(${baseDestinyNumber} + 岁${age}×113 + 刻${ke.code}×29) mod 12000 = ${rawFormulaCode}"
            val toneMarker = TiebanVersesLibrary.getToneMarker(rawFormulaCode)

            val template = TiebanVersesLibrary.getTemplate(rawFormulaCode + age)

            yearlyVerses.add(
                YearlyVerse(
                    age = age,
                    year = currYear,
                    ganZhi = ganZhi,
                    naYin = naYin,
                    verseCode = rawFormulaCode,
                    toneMarker = toneMarker,
                    formula = formulaText,
                    originalVerse = template.originalVerse,
                    originalJudgment = template.originalJudgment,
                    correctedVerse = template.correctedVerse,
                    correctedJudgment = template.correctedJudgment,
                    fortuneLevel = template.fortuneLevel,
                    aspect = template.aspect
                )
            )
        }

        return TiebanCalculationResult(
            subjectName = name.ifBlank { "求测缘主" },
            gender = gender,
            birthSolarDate = chart.solarDateString,
            birthLunarDate = chart.lunarDateString,
            baZiChart = chart,
            keDivision = ke,
            inquiryDate = inquiryDate,
            innateFateNumber = innateFateNumber,
            fiveTonesNumber = fiveTonesNumber,
            fiveTonesLabel = fiveTonesLabel,
            dayFateNumber = dayFateNumber,
            hourFortuneNumber = hourFortuneNumber,
            baseDestinyNumber = baseDestinyNumber,
            twelveGua = twelveGua,
            kaoKeSummary = kaoKeSummary,
            generalDestinyPoem = generalDestinyPoem,
            generalDestinySummary = generalDestinySummary,
            yearlyVerses = yearlyVerses
        )
    }
}
