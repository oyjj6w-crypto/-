package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TiebanDatabase
import com.example.data.TiebanRecord
import com.example.data.TiebanRepository
import com.example.engine.LunarSolarEngine
import com.example.engine.TiebanShenshuEngine
import com.example.model.BaZiChart
import com.example.model.Gender
import com.example.model.KeDivision
import com.example.model.TiebanCalculationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class CalendarType(val label: String) {
    SOLAR("公历 (阳历)"),
    LUNAR("农历 (阴历)")
}

enum class AgeRangeFilter(val label: String, val startAge: Int, val endAge: Int) {
    ALL("全部岁数 (1-100)", 1, 100),
    CHILDHOOD("启蒙总角 (1-18岁)", 1, 18),
    YOUTH("志学而立 (19-35岁)", 19, 35),
    MIDDLE_AGE("不惑知命 (36-55岁)", 36, 55),
    MATURE_AGE("耳顺从心 (56-75岁)", 56, 75),
    SENIOR_AGE("寿考耄耋 (76-100岁)", 76, 100)
}

class TiebanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TiebanRepository

    init {
        val dao = TiebanDatabase.getDatabase(application).tiebanDao()
        repository = TiebanRepository(dao)
    }

    val savedRecords: StateFlow<List<TiebanRecord>> = repository.allRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 录入状态
    private val _subjectName = MutableStateFlow("缘主")
    val subjectName: StateFlow<String> = _subjectName.asStateFlow()

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender: StateFlow<Gender> = _gender.asStateFlow()

    private val _calendarType = MutableStateFlow(CalendarType.SOLAR)
    val calendarType: StateFlow<CalendarType> = _calendarType.asStateFlow()

    private val _birthYear = MutableStateFlow(1990)
    val birthYear: StateFlow<Int> = _birthYear.asStateFlow()

    private val _birthMonth = MutableStateFlow(5)
    val birthMonth: StateFlow<Int> = _birthMonth.asStateFlow()

    private val _birthDay = MutableStateFlow(18)
    val birthDay: StateFlow<Int> = _birthDay.asStateFlow()

    private val _birthHour = MutableStateFlow(12)
    val birthHour: StateFlow<Int> = _birthHour.asStateFlow()

    private val _birthMinute = MutableStateFlow(30)
    val birthMinute: StateFlow<Int> = _birthMinute.asStateFlow()

    private val _keDivision = MutableStateFlow(KeDivision.CHU_3)
    val keDivision: StateFlow<KeDivision> = _keDivision.asStateFlow()

    private val _inquiryDate = MutableStateFlow(
        SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
    )
    val inquiryDate: StateFlow<String> = _inquiryDate.asStateFlow()

    // 实时换算八字四柱
    private val _currentChart = MutableStateFlow(
        LunarSolarEngine.calculateChart(1990, 5, 18, 12, 30)
    )
    val currentChart: StateFlow<BaZiChart> = _currentChart.asStateFlow()

    // 测算结果
    private val _calculationResult = MutableStateFlow<TiebanCalculationResult?>(null)
    val calculationResult: StateFlow<TiebanCalculationResult?> = _calculationResult.asStateFlow()

    // 当前Tab导航: 0: 排盘起数, 1: 核心数理, 2: 百岁断语, 3: 档案历史
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // 断语筛选与搜索
    private val _verseSearchQuery = MutableStateFlow("")
    val verseSearchQuery: StateFlow<String> = _verseSearchQuery.asStateFlow()

    private val _ageFilter = MutableStateFlow(AgeRangeFilter.ALL)
    val ageFilter: StateFlow<AgeRangeFilter> = _ageFilter.asStateFlow()

    private val _statusToast = MutableStateFlow<String?>(null)
    val statusToast: StateFlow<String?> = _statusToast.asStateFlow()

    init {
        // 初始自动测算一次展示给用户
        calculateTieban()
    }

    fun setSubjectName(name: String) {
        _subjectName.value = name
    }

    fun setGender(g: Gender) {
        _gender.value = g
        recalculateLiveChart()
    }

    fun setCalendarType(type: CalendarType) {
        _calendarType.value = type
        recalculateLiveChart()
    }

    fun setBirthYear(y: Int) {
        _birthYear.value = y.coerceIn(1900, 2100)
        recalculateLiveChart()
    }

    fun setBirthMonth(m: Int) {
        _birthMonth.value = m.coerceIn(1, 12)
        recalculateLiveChart()
    }

    fun setBirthDay(d: Int) {
        _birthDay.value = d.coerceIn(1, 31)
        recalculateLiveChart()
    }

    fun setBirthHour(h: Int) {
        _birthHour.value = h.coerceIn(0, 23)
        // 自动根据分钟判定考刻推荐
        updateKeByMinute(_birthMinute.value)
        recalculateLiveChart()
    }

    fun setBirthMinute(min: Int) {
        _birthMinute.value = min.coerceIn(0, 59)
        updateKeByMinute(_birthMinute.value)
        recalculateLiveChart()
    }

    fun setKeDivision(ke: KeDivision) {
        _keDivision.value = ke
    }

    private fun updateKeByMinute(min: Int) {
        // 双小时中前半时辰为初刻(0-59)，后半时辰为正刻(60-119)，每15分钟一刻
        val isFirstHour = (_birthHour.value % 2 != 0) // 如子时23-00，23点是前半时辰
        val offset = min / 15
        val targetCode = if (isFirstHour) offset else offset + 4
        val matchedKe = KeDivision.values().find { it.code == targetCode } ?: KeDivision.CHU_1
        _keDivision.value = matchedKe
    }

    private fun recalculateLiveChart() {
        val y = _birthYear.value
        val m = _birthMonth.value
        val d = _birthDay.value
        val h = _birthHour.value
        val min = _birthMinute.value

        val chart = if (_calendarType.value == CalendarType.SOLAR) {
            LunarSolarEngine.calculateChart(y, m, d, h, min)
        } else {
            // 农历转公历近似映射换算
            val approxSolarMonth = (m % 12) + 1
            LunarSolarEngine.calculateChart(y, approxSolarMonth, d.coerceAtMost(28), h, min)
        }
        _currentChart.value = chart
    }

    fun calculateTieban() {
        recalculateLiveChart()
        val chart = _currentChart.value
        val res = TiebanShenshuEngine.calculate(
            name = _subjectName.value,
            gender = _gender.value,
            chart = chart,
            ke = _keDivision.value,
            inquiryDate = _inquiryDate.value,
            birthYear = _birthYear.value
        )
        _calculationResult.value = res
    }

    fun calculateAndNavigateToVerses() {
        calculateTieban()
        _selectedTab.value = 2 // 跳转至双栏断语页
        _statusToast.value = "已完成铁板神数排盘与百岁断语推演"
    }

    fun calculateAndNavigateToMetrics() {
        calculateTieban()
        _selectedTab.value = 1 // 跳转至数理指标页
        _statusToast.value = "核心数理与辟卦推演已生成"
    }

    fun selectTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun setVerseSearchQuery(query: String) {
        _verseSearchQuery.value = query
    }

    fun setAgeFilter(filter: AgeRangeFilter) {
        _ageFilter.value = filter
    }

    fun clearStatusToast() {
        _statusToast.value = null
    }

    fun saveCurrentChart() {
        val res = _calculationResult.value ?: return
        viewModelScope.launch {
            val record = TiebanRecord(
                subjectName = res.subjectName,
                gender = res.gender.designation,
                birthYear = _birthYear.value,
                birthMonth = _birthMonth.value,
                birthDay = _birthDay.value,
                birthHour = _birthHour.value,
                birthMinute = _birthMinute.value,
                keCode = _keDivision.value.code,
                solarDateString = res.birthSolarDate,
                lunarDateString = res.birthLunarDate,
                baziString = "${res.baZiChart.yearPillar.fullText} ${res.baZiChart.monthPillar.fullText} ${res.baZiChart.dayPillar.fullText} ${res.baZiChart.hourPillar.fullText}",
                innateFateNumber = res.innateFateNumber,
                fiveTonesNumber = res.fiveTonesNumber,
                dayFateNumber = res.dayFateNumber,
                hourFortuneNumber = res.hourFortuneNumber,
                baseDestinyNumber = res.baseDestinyNumber,
                twelveGuaName = res.twelveGua.name
            )
            repository.saveRecord(record)
            _statusToast.value = "命盘档案已妥善保存于古籍书匣"
        }
    }

    fun loadFromRecord(record: TiebanRecord) {
        _subjectName.value = record.subjectName
        _gender.value = if (record.gender.contains("乾") || record.gender.contains("男")) Gender.MALE else Gender.FEMALE
        _birthYear.value = record.birthYear
        _birthMonth.value = record.birthMonth
        _birthDay.value = record.birthDay
        _birthHour.value = record.birthHour
        _birthMinute.value = record.birthMinute
        _keDivision.value = KeDivision.values().find { it.code == record.keCode } ?: KeDivision.CHU_1
        recalculateLiveChart()
        calculateTieban()
        _selectedTab.value = 1 // 跳转至数理页面
        _statusToast.value = "已载入【${record.subjectName}】的命盘记录"
    }

    fun deleteRecord(record: TiebanRecord) {
        viewModelScope.launch {
            repository.deleteRecord(record)
            _statusToast.value = "档案已从书匣中移除"
        }
    }
}
