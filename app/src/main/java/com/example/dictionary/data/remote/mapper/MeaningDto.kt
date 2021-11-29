package com.example.dictionary.data.remote.mapper

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
)