package com.example.finalprojectkotlin.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.finalprojectkotlin.databinding.FragmentFilmDetailBinding
import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.viewmodel.FilmViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!
    private var imdbId : String? = null
    private val viewModel by viewModel<FilmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            imdbId = FilmDetailFragmentArgs.fromBundle(it).imdbId
            imdbId?.let {
                viewModel.getDataById(imdbId!!)
            }
            observeLiveData()
        }

    }

    fun setData(film : Film?){
        binding.filmNameTextView.text = film?.filmName ?: ""
        binding.filmYearTextView.text = film?.filmYear ?: ""
        binding.filmCountry.text = film?.filmCountry ?: ""
        binding.filmArtistNames.text = film?.filmActors ?: ""
        binding.filmDirector.text = "Director : " + film?.filmDirector ?: ""
        binding.filmImdb.text = "IMDB : " + film?.filmImdbRating ?: ""

        film?.filmImageUrl?.let {
            val imageUri : Uri = Uri.parse(film?.filmImageUrl)
            Picasso.get().load(imageUri).into(binding.imageView)
        }

    }

    private fun observeLiveData(){
        viewModel.film.observe(viewLifecycleOwner, Observer { film ->
            setData(film.data ?: null)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}