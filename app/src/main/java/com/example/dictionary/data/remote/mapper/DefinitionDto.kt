package com.example.dictionary.data.remote.mapper

import com.example.dictionary.data.model.Definition

data class DefinitionDto(
    val antonyms: List<Any>,
    val definition: String,
    val example: String,
    val synonyms: List<String>
) {
    /**
     * Maps DefinitionDto data to Definition object.
     */
    fun toDefinition(): Definition {
        return Definition(
            definition, example
        )
    }
}