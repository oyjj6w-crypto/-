package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tieban_records")
data class TiebanRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectName: String,
    val gender: String,
    val birthYear: Int,
    val birthMonth: Int,
    val birthDay: Int,
    val birthHour: Int,
    val birthMinute: Int,
    val keCode: Int,
    val solarDateString: String,
    val lunarDateString: String,
    val baziString: String, // e.g. "庚午 辛巳 丁亥 丙午"
    val innateFateNumber: Int,
    val fiveTonesNumber: Int,
    val dayFateNumber: Int,
    val hourFortuneNumber: Int,
    val baseDestinyNumber: Int,
    val twelveGuaName: String,
    val createdAt: Long = System.currentTimeMillis()
)
