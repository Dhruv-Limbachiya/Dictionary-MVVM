package com.example.dictionary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.data.model.Meaning
import com.example.dictionary.data.remote.mapper.MeaningDto
import com.example.dictionary.data.remote.mapper.PhoneticDto

/**
 * Created By Dhruv Limbachiya on 29-11-2021 05:53 PM.
 */

/**
 * Represent an individual record in the database.
 */
@Entity(tableName = "word")
data class WordItemEntity (
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val word: String,
    @PrimaryKey(autoGenerate = true)
    val id: String? = null
)
