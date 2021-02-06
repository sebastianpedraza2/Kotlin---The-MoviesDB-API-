package com.example.moviesapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.moviesapp.R
import com.example.moviesapp.core.Resource
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.remote.MovieDataSource
import com.example.moviesapp.databinding.FragmentMovieBinding
import com.example.moviesapp.presentation.MovieViewModel
import com.example.moviesapp.presentation.MovieViewModelFactory
import com.example.moviesapp.repository.MovieRepositoryImpl
import com.example.moviesapp.repository.RetrofitClient
import com.example.moviesapp.ui.movie.adapters.MovieAdapter
import com.example.moviesapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.moviesapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.moviesapp.ui.movie.adapters.concat.UpcomingConcatAdapter


class FragmentMovie : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {
    private lateinit var bindind: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webService)
            )
        )
    }
    private lateinit var concatAdapter: ConcatAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindind = FragmentMovieBinding.bind(view)
        concatAdapter = ConcatAdapter()

        viewModel.fechMainMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    bindind.relativeProgress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    bindind.relativeProgress.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@FragmentMovie
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@FragmentMovie
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@FragmentMovie
                                )
                            )
                        )
                    }
                    bindind.rvMovies.adapter = concatAdapter

                }
                is Resource.Failure -> {
                    bindind.relativeProgress.visibility = View.GONE
                    Log.d("LiveData", "Error: ${result.exception}")
                }
            }
        })
    }


    override fun OnMovieclick(movie: Movie) {
        val action = FragmentMovieDirections.actionFragmentMovieToFragmentMovieDetail(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}

