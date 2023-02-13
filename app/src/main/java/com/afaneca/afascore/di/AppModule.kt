package com.afaneca.afascore.di

import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.data.remote.AfaScoreAPI
import com.afaneca.afascore.data.repository.LiveMatchesRepository
import com.afaneca.afascore.domain.repository.MatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by António Faneca on 2/13/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAfaScoreApi(): AfaScoreAPI = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AfaScoreAPI::class.java)


    @Provides
    @Singleton
    fun provideMatchesRepository(api: AfaScoreAPI): MatchesRepository = LiveMatchesRepository(api)
}