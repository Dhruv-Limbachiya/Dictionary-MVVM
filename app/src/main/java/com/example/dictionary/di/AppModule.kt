package com.example.dictionary.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.dictionary.BuildConfig
import com.example.dictionary.data.local.db.Converter
import com.example.dictionary.data.local.db.WordInfoDatabase
import com.example.dictionary.data.local.util.GsonParser
import com.example.dictionary.data.remote.DictionaryApi
import com.example.dictionary.repository.WordInfoRepository
import com.example.dictionary.repository.WordInfoRepositoryImpl
import com.example.dictionary.util.Constants
import com.example.dictionary.util.Constants.NETWORK_READ_TIME_OUT
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created By Dhruv Limbachiya on 29-11-2021 12:21 PM.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Network layer dependencies
    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("DictionaryLogger", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            readTimeout(NETWORK_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(NETWORK_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideDictionaryApi(okHttpClient: OkHttpClient): DictionaryApi {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
            baseUrl(BuildConfig.DICTIONARY_BASE_URL)
        }.build().create(DictionaryApi::class.java)
    }


    // Database Layer dependencies.
    @Singleton
    @Provides
    fun provideTypeConverter(): Converter {
        return Converter(GsonParser(Gson()))
    }

    @Singleton
    @Provides
    fun provideWordInfoDatabase(
        @ApplicationContext context: Context,
        typeConverter: Converter
    ): WordInfoDatabase {
        return Room.databaseBuilder(
            context,
            WordInfoDatabase::class.java,
            Constants.DATABASE_NAME
        ).addTypeConverter(typeConverter).build()
    }

    @Singleton
    @Provides
    fun provideWordInfoRepository(
        dictionaryApi: DictionaryApi,
        database: WordInfoDatabase
    ) : WordInfoRepository {
        return WordInfoRepositoryImpl(
            dictionaryApi,
            database
        )
    }
}
