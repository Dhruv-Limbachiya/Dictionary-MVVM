package com.example.dictionary.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionary.data.local.dao.WordInfoDao
import com.example.dictionary.data.local.entities.WordItemEntity

/**
 * Created By Dhruv Limbachiya on 29-11-2021 06:58 PM.
 */

@Database(
    entities = [WordItemEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class WordInfoDatabase : RoomDatabase() {
    abstract val wordInfoDao: WordInfoDao
}