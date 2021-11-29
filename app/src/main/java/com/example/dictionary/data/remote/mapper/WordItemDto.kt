package com.example.dictionary.data.remote.mapper

import com.example.dictionary.data.local.entities.WordItemEntity

data class WordItemDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {

    /**
     * Maps the response(list of word items) coming from the API into WordItemEntity for local caching.
     */
    fun toWordEntity() : WordItemEntity {
        return WordItemEntity(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}