package com.example.moviesapp.repository

import com.example.moviesapp.data.model.MovieList
import com.example.moviesapp.data.remote.MovieDataSource

class MovieRepositoryImpl(val dataSource: MovieDataSource) : MovieRepository{

    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}