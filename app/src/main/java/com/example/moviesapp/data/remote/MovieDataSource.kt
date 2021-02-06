package com.example.moviesapp.data.remote

import com.example.moviesapp.data.model.MovieList
import com.example.moviesapp.repository.WebService
import com.example.moviesapp.utils.AppConstants

class MovieDataSource(val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList =webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}