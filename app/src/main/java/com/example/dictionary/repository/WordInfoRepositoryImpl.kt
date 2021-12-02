package com.example.dictionary.repository

import android.util.Log
import com.example.dictionary.data.local.dao.WordInfoDao
import com.example.dictionary.data.local.db.WordInfoDatabase
import com.example.dictionary.data.remote.DictionaryApi
import com.example.dictionary.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created By Dhruv Limbachiya on 30-11-2021 11:23 AM.
 */
class WordInfoRepositoryImpl  @Inject constructor(
    private val api: DictionaryApi,
    private val database: WordInfoDatabase
) : WordInfoRepository {

    override fun getWordInfo(word: String) = flow {
        emit(Resource.Loading())

        // Get the word info or list of word info if the specified word contains in the database.
        val localWordsInfo = database.wordInfoDao.getWords(word)

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
                emit(Resource.Error("Oops,something went wrong!", localWordsInfo))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        "Couldn't reach the server.Check your internet connection",
                        localWordsInfo
                    )
                )
            }
        }
    }
}