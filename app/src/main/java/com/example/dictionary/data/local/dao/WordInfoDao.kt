package com.example.dictionary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.local.entities.WordItemEntity

/**
 * Created By Dhruv Limbachiya on 29-11-2021 07:01 PM.
 */

@Dao
interface WordInfoDao {

    @Insert
    suspend fun insertWords(words: List<WordItemEntity>)

    @Query("SELECT * FROM word_entity WHERE word LIKE '%' || :word || '%' ")  // "SELECT * FROM word_entity where word LIKE '%word%' "
    fun getWords(word: String): List<WordItemEntity>?

    /**
     * Deletes the list of word when particular word of word_entity matches with the values(words) passed in the IN() condition/operator.
     */
    @Query("DELETE FROM word_entity WHERE word IN(:words) ")
    suspend fun deleteWords(words: List<String>)
}

