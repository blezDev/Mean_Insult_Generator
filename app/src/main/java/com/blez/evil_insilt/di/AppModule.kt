package com.blez.evil_insilt.di

import android.content.Context
import com.blez.evil_insilt.data.api.InsultAPI
import com.blez.evil_insilt.repository.InsultRepository
import com.blez.evil_insilt.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .build()

    }

    @Provides
    @Singleton
    fun providesInsultAPI(okHttpClient: OkHttpClient) : InsultAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(InsultAPI::class.java)

    }

    @Provides
    fun providesContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun providesInsultRepository(api: InsultAPI,context : Context) = InsultRepository(api, context)
}