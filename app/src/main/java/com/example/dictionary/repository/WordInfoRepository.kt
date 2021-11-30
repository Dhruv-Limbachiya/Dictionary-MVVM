package com.example.dictionary.repository

import com.example.dictionary.data.local.entities.WordItemEntity
import com.example.dictionary.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created By Dhruv Limbachiya on 30-11-2021 11:07 AM.
 */
interface WordInfoRepository {
    fun getWordInfo(word: String) : Flow<Resource<List<WordItemEntity>>>
}