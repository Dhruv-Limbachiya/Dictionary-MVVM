package com.example.dictionary.data.remote.mapper

data class DefinitionDto(
    val antonyms: List<Any>,
    val definition: String,
    val example: String,
    val synonyms: List<String>
)