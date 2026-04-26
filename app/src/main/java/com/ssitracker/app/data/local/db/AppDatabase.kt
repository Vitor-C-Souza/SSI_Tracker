package com.ssitracker.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssitracker.app.data.local.dao.SSIDao
import com.ssitracker.app.data.local.entity.SSIEntity

@Database(entities = [SSIEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun SSIDao(): SSIDao
}