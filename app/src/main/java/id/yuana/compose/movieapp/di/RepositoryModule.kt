package id.yuana.compose.movieapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.yuana.compose.movieapp.data.repository.MovieRepositoryImpl
import id.yuana.compose.movieapp.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsMovieRepository(
        movieRepository: MovieRepositoryImpl
    ): MovieRepository
}