package com.example.finalprojectkotlin.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectkotlin.databinding.FilmListItemBinding
import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.view.FilmDetailFragment
import com.example.finalprojectkotlin.view.FilmListFragmentDirections
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(val filmList : ArrayList<Film>, val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    interface Listener{
        fun onItemClick(film : Film)
    }

    class RowHolder(val binding : FilmListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = FilmListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(filmList[position])
        }
        holder.binding.recyclerFilmName.text = filmList[position].filmName
        holder.binding.recyclerFilmYear.text = filmList[position].filmYear
        val imgUri : Uri = Uri.parse(filmList[position].filmImageUrl)
        Picasso.get().load(imgUri).into(holder.binding.recycleImageView)

        holder.itemView.setOnClickListener {
            val action = FilmListFragmentDirections.actionFilmListFragmentToFilmDetailFragment(filmList[position].filmId)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return filmList.size
    }
}