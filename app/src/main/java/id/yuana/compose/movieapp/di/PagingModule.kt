package id.yuana.compose.movieapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.yuana.compose.movieapp.data.paging.MovieRemotePagingSource
import id.yuana.compose.movieapp.data.remote.MovieApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PagingModule {

    @Provides
    @Singleton
    fun provideMoviePagingSource(
        movieApi: MovieApi
    ) = MovieRemotePagingSource(movieApi)
}