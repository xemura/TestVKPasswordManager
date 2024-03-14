package com.xenia.testvk.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xenia.testvk.data.entities.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract val passwordDao: PasswordDao
}