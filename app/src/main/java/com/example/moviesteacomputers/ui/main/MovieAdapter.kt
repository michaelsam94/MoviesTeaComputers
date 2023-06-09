package com.example.moviesteacomputers.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.moviesteacomputers.R
import com.example.moviesteacomputers.databinding.ItemMovieBinding
import com.example.moviesteacomputers.ui.model.Movie

class MovieAdapter(private val movies: List<Movie>,private val onClick :(movie: Movie)->Unit) : RecyclerView.Adapter<MovieAdapter.MovieVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH =
        MovieVH(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))


    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bindView(movies[position])
    }

    override fun getItemCount(): Int = if(movies.isEmpty()) 0 else movies.size

    inner class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bindView(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(binding.root.context)
                .load(movie.image)
                .placeholder(circularProgressDrawable)
                .into(binding.ivPoster)
            binding.content.setOnClickListener { onClick.invoke(movie) }
        }
    }

}

