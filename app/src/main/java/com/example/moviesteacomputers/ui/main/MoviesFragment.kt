package com.example.moviesteacomputers.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesteacomputers.R
import com.example.moviesteacomputers.databinding.FragmentMoviesBinding
import com.example.moviesteacomputers.helper.Progress
import com.example.moviesteacomputers.helper.Success
import com.example.moviesteacomputers.helper.Error
import com.example.moviesteacomputers.ui.model.Movie
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var adapter: MovieAdapter
    private lateinit var binding: FragmentMoviesBinding


    private val viewModel: MainViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideUpButton()
        (activity as MainActivity).showSearch()


        binding = FragmentMoviesBinding.bind(view)

        viewModel.mainViewState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Success<*> -> {
                    val movies: List<Movie> = it.data as List<Movie>
                    adapter = MovieAdapter(movies) {
                        findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(it))
                    }
                    binding.rvMovies.adapter = adapter
                    binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvMovies.setHasFixedSize(true)
                    binding.loadingBar.visibility = View.GONE
                }
                is Progress -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is Error -> {
                    binding.loadingBar.visibility = View.GONE
                    Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
        //viewModel.getMoviesCoroutine()
        viewModel.getTrending()


    }



}