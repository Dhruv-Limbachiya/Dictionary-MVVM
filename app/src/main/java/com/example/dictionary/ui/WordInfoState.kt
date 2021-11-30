package com.example.dictionary.ui

import com.example.dictionary.data.local.entities.WordItemEntity

/**
 * Created By Dhruv Limbachiya on 30-11-2021 05:58 PM.
 */

/**
 * Represent the state of main screen.
 */
data class WordInfoState(
    var wordsInfo: List<WordItemEntity>? = emptyList(),
    var isLoading: Boolean = false
)