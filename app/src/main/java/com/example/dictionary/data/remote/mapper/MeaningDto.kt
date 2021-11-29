package com.example.dictionary.data.remote.mapper

import com.example.dictionary.data.model.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
) {

    /**
     * Maps MeaningDto data to Meaning object.
     */
    fun toMeaning() : Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}