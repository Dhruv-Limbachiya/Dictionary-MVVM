package com.example.dictionary.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import com.example.dictionary.data.remote.mapper.WordItemDto

/**
 * Created By Dhruv Limbachiya on 29-11-2021 11:49 AM.
 */
interface DictionaryApi {

    @GET("/en/{word}")
    suspend fun searchWord(
        @Path("word") word: String
    ): List<WordItemDto>
}