package com.example.data

import kotlinx.coroutines.flow.Flow

class TiebanRepository(private val dao: TiebanDao) {
    val allRecords: Flow<List<TiebanRecord>> = dao.getAllRecords()

    suspend fun saveRecord(record: TiebanRecord): Long {
        return dao.insertRecord(record)
    }

    suspend fun deleteRecord(record: TiebanRecord) {
        dao.deleteRecord(record)
    }

    suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    suspend fun getRecordById(id: Long): TiebanRecord? {
        return dao.getRecordById(id)
    }
}
