package com.alpersevindik.tanews.di

import android.content.Context
import androidx.room.Room
import com.alpersevindik.tanews.BuildConfig
import com.alpersevindik.tanews.data.local.Database
import com.alpersevindik.tanews.data.network.Constants
import com.alpersevindik.tanews.data.network.NYTService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG)
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    else
        OkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideNYTService(retrofit: Retrofit) = retrofit.create(NYTService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        Database::class.java, Database.DB_NAME
    ).build()
}