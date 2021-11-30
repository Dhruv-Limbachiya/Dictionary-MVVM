package com.example.dictionary.data.remote.mapper

import com.example.dictionary.data.model.Definition

data class DefinitionDto(
    val antonyms: List<String>? = null,
    val definition: String? = null,
    val example: String? = null,
    val synonyms: List<String>? = null
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