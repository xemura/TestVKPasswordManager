package com.xenia.testvk.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xenia.testvk.data.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Query("SELECT * FROM password")
    fun allPasswords(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM password WHERE id=:id")
    fun getPasswordById(id: Int): ItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassword(password: ItemEntity)

    @Delete
    suspend fun deletePassword(password: ItemEntity)
}