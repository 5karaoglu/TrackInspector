package com.besirkaraoglu.trackinspector.application.di

import android.content.Context
import androidx.room.Room
import com.besirkaraoglu.trackinspector.application.Constants
import com.besirkaraoglu.trackinspector.data.local.AppDatabase
import com.besirkaraoglu.trackinspector.data.remote.WebService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,
    AppDatabase::class.java,
    Constants.DATABASE_NAME).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase) = appDatabase.tracksDao()

    @Singleton
    @Provides
    fun provideRetrofitInstance() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(Constants.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)
}