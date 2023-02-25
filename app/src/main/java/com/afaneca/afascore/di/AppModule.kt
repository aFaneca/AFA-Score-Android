package com.afaneca.afascore.di

import android.content.Context
import androidx.room.Room
import com.afaneca.afascore.BuildConfig
import com.afaneca.afascore.common.Constants
import com.afaneca.afascore.data.local.FilterDataStorePreferences
import com.afaneca.afascore.data.local.db.LocalDatabase
import com.afaneca.afascore.data.local.db.favorites.FavoriteDao
import com.afaneca.afascore.data.remote.AfaScoreAPI
import com.afaneca.afascore.data.repository.LiveFavoritesRepository
import com.afaneca.afascore.data.repository.LiveFilterRepository
import com.afaneca.afascore.data.repository.LiveMatchesRepository
import com.afaneca.afascore.domain.repository.FavoritesRepository
import com.afaneca.afascore.domain.repository.FilterRepository
import com.afaneca.afascore.domain.repository.MatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by António Faneca on 2/13/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    /* HTTP */
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideAfaScoreApi(httpClient: OkHttpClient): AfaScoreAPI = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(AfaScoreAPI::class.java)


    /* DATABASE */
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, "afascore-db").build()

    @Provides
    @Singleton
    fun provideFavoriteDao(db: LocalDatabase) = db.favoriteDao()

    /* REPOSITORIES */
    @Provides
    @Singleton
    fun provideMatchesRepository(api: AfaScoreAPI): MatchesRepository = LiveMatchesRepository(api)

    @Provides
    @Singleton
    fun provideFilterRepository(filterDataStore: FilterDataStorePreferences): FilterRepository =
        LiveFilterRepository(filterDataStore)

    @Provides
    @Singleton
    fun provideFavoritesRepository(favoriteDao: FavoriteDao): FavoritesRepository =
        LiveFavoritesRepository(favoriteDao)
}