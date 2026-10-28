package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TiebanRecord::class], version = 1, exportSchema = false)
abstract class TiebanDatabase : RoomDatabase() {
    abstract fun tiebanDao(): TiebanDao

    companion object {
        @Volatile
        private var INSTANCE: TiebanDatabase? = null

        fun getDatabase(context: Context): TiebanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TiebanDatabase::class.java,
                    "tieban_shenshu.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
