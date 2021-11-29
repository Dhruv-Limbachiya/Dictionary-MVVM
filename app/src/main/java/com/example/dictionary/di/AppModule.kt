package com.example.dictionary.di

import android.util.Log
import com.example.dictionary.BuildConfig
import com.example.dictionary.data.remote.DictionaryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    private const val NETWORK_READ_TIME_OUT = 360

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

}
