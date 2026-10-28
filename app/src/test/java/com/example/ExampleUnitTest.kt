package com.example

import com.example.engine.LunarSolarEngine
import com.example.engine.TiebanShenshuEngine
import com.example.model.Gender
import com.example.model.KeDivision
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testBaZiCalculation() {
        val chart = LunarSolarEngine.calculateChart(1990, 5, 18, 12, 30)
        assertNotNull(chart.yearPillar)
        assertEquals("庚", chart.yearPillar.stem)
        assertEquals("午", chart.yearPillar.branch)
        assertNotNull(chart.monthPillar)
        assertNotNull(chart.dayPillar)
        assertNotNull(chart.hourPillar)
    }

    @Test
    fun testTiebanCalculationAnd100Verses() {
        val chart = LunarSolarEngine.calculateChart(1990, 5, 18, 12, 30)
        val result = TiebanShenshuEngine.calculate(
            name = "李天机",
            gender = Gender.MALE,
            chart = chart,
            ke = KeDivision.CHU_3,
            inquiryDate = "2026-10-28",
            birthYear = 1990
        )

        assertEquals("李天机", result.subjectName)
        assertTrue(result.innateFateNumber > 0)
        assertTrue(result.baseDestinyNumber > 0)
        assertEquals(100, result.yearlyVerses.size)

        // 验证首尾流年条文
        val firstYear = result.yearlyVerses.first()
        assertEquals(1, firstYear.age)
        assertEquals(1990, firstYear.year)
        assertTrue(firstYear.originalVerse.isNotBlank())
        assertTrue(firstYear.correctedJudgment.isNotBlank())

        val lastYear = result.yearlyVerses.last()
        assertEquals(100, lastYear.age)
        assertEquals(2089, lastYear.year)
        assertTrue(lastYear.originalVerse.isNotBlank())
        assertTrue(lastYear.correctedJudgment.isNotBlank())
    }
}
