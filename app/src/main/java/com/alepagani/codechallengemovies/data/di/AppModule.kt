package com.alepagani.codechallengemovies.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.alepagani.codechallengemovies.core.AppConstant.BASE_URL
import com.alepagani.codechallengemovies.data.RepositoryImpl
import com.alepagani.codechallengemovies.data.local.AppDataBase
import com.alepagani.codechallengemovies.data.local.LocalMovieDataSource
import com.alepagani.codechallengemovies.data.local.MovieDao
import com.alepagani.codechallengemovies.data.remote.MovieApi
import com.alepagani.codechallengemovies.data.remote.RemoteMovieDataSource
import com.alepagani.codechallengemovies.domain.Repository
import com.alepagani.codechallengeyape.data.interceptor.AuthInterceptor
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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): MovieDao {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "movies"
        ).build().movieDao()
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideRepository(localMovieDataSource: LocalMovieDataSource, remoteMovieDataSource: RemoteMovieDataSource, sharedPreferences: SharedPreferences): Repository {
        return RepositoryImpl(localMovieDataSource, remoteMovieDataSource, sharedPreferences)
    }

    @Provides
    fun provideLocalDataSource(movieDato: MovieDao) : LocalMovieDataSource {
        return LocalMovieDataSource(movieDato)
    }

    @Provides
    fun provideRemoteDataSource(movieApi: MovieApi): RemoteMovieDataSource {
        return RemoteMovieDataSource(movieApi)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }
}