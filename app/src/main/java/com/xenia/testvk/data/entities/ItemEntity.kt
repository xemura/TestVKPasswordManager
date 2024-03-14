package com.xenia.testvk.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password")
data class ItemEntity(
    @PrimaryKey val id: Int = 0, // auto generated
    val imageUrl: String,
    val websiteUrl: String,
    val login: String,
    val password: String,
)