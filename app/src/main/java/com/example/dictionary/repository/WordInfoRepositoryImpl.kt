package com.example.dictionary.repository

import com.example.dictionary.data.local.db.WordInfoDatabase
import com.example.dictionary.data.local.entities.WordItemEntity
import com.example.dictionary.data.remote.DictionaryApi
import com.example.dictionary.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created By Dhruv Limbachiya on 30-11-2021 11:23 AM.
 */
class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val database: WordInfoDatabase
) : WordInfoRepository {

    /**
     * Method is responsible for retrieving word or list of words info either from the database or from the remote API.
     */
    override fun getWordInfo(word: String) = flow {
        emit(Resource.Loading())

        // If user input is blank("") then simply return an emptyList.
        if (word.isBlank()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }

        // Get the word info about the specified word from the db.
        val localWordsInfo = database.wordInfoDao.getWords(word)

        /**
         * if user entered word is already present in db then emit that words with its details.
         * else make call to api to get the words details from the server.
         */
        if (localWordsInfo?.isNotEmpty() == true) {
            emit(Resource.Success(localWordsInfo))
        } else {
            try {
                val apiWordsInfo = api.searchWord(word).map { it.toWordEntity() }
                // Deletes the word info or list of word info if the specified word match with word contain in the database.
                database.wordInfoDao.deleteWords(apiWordsInfo.map { it.word })
                // Insert words data into the db.
                database.wordInfoDao.insertWords(apiWordsInfo)
                // Emit the words info
                emit(Resource.Success(apiWordsInfo))
            } catch (e: HttpException) {
                emit(Resource.Error("Oops,something went wrong!", emptyList<WordItemEntity>()))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        "Couldn't reach the server.Check your internet connection",
                        emptyList<WordItemEntity>()
                    )
                )
            }
        }
    }
}