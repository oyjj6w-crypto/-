package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TiebanDao {
    @Query("SELECT * FROM tieban_records ORDER BY createdAt DESC")
    fun getAllRecords(): Flow<List<TiebanRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: TiebanRecord): Long

    @Delete
    suspend fun deleteRecord(record: TiebanRecord)

    @Query("DELETE FROM tieban_records WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM tieban_records WHERE id = :id LIMIT 1")
    suspend fun getRecordById(id: Long): TiebanRecord?
}
