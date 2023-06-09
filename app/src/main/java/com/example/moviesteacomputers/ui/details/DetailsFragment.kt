package com.example.moviesteacomputers.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.moviesteacomputers.R
import com.example.moviesteacomputers.databinding.FragmentDetailsBinding
import com.example.moviesteacomputers.ui.main.MainActivity

class DetailsFragment: Fragment(R.layout.fragment_details) {

    lateinit var binding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showUpButton()
        (activity as MainActivity).hideSearch()

        binding = FragmentDetailsBinding.bind(view)

        binding.titleTv.text = args.movie.title
        binding.overViewTv.text = args.movie.overview
        val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(binding.root.context)
            .load(args.movie.image)
            .placeholder(circularProgressDrawable)
            .into(binding.posterIv)

        binding.languageTv.text = args.movie.language
        binding.ratingTv.text = String.format("%.1f",args.movie.voteAverage).plus("/10")
        binding.releaseDateTv.text = args.movie.releaseDate


    }
}