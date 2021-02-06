package com.example.moviesapp.ui.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.ui.movie.FragmentMovie
import com.example.moviesapp.ui.movie.FragmentMovieDirections


class FragmentMovieDetail : Fragment(R.layout.fragment_movie_detail) {
    private val args by navArgs<FragmentMovieDetailArgs>()
    private lateinit var binding: FragmentMovieDetailBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        binding.txtMovieName.text = args.title
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/${args.backGroudImageUrl}").centerCrop()
            .into(binding.imgBackground)

        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}")
            .centerCrop().into(binding.imgMovie)
        binding.txtDescription.text = args.overView
        binding.txtlanguage.text = args.language
        binding.txtReview.text = "${args.voteAverage} (${args.voteCount}) Reviews"
        binding.txtreleased.text = args.releaseDate
    }


}