package com.example.finalprojectkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectkotlin.adapter.RecyclerViewAdapter
import com.example.finalprojectkotlin.databinding.FragmentFilmListBinding
import com.example.finalprojectkotlin.model.Film
import com.example.finalprojectkotlin.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel



class FilmListFragment : Fragment(), RecyclerViewAdapter.Listener{
    private var _binding: FragmentFilmListBinding? = null
    private val binding get() = _binding!!

    private var recyclerViewAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private val viewModel by viewModel<FilmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        viewModel.getData()

        observeLiveData()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getDataByName(query)
                    observeLiveData()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeLiveData(){
        viewModel.filmList.observe(viewLifecycleOwner, Observer { filmList ->
            binding.recyclerView.visibility = View.VISIBLE
            binding.filmErrorText.visibility = View.GONE
            binding.filmProgressBar.visibility = View.GONE
            recyclerViewAdapter = RecyclerViewAdapter(ArrayList(filmList.data ?: arrayListOf()),this@FilmListFragment)
            binding.recyclerView.adapter = recyclerViewAdapter
        })

        viewModel.filmError.observe(viewLifecycleOwner, Observer { error ->
            if(error.data == true){
                binding.filmErrorText.text = error.message
                binding.filmErrorText.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }else{
                binding.filmErrorText.visibility = View.GONE
            }
        })

        viewModel.filmLoading.observe(viewLifecycleOwner, Observer { loading ->
            if(loading.data == true){
                binding.filmProgressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.filmErrorText.visibility = View.GONE
            }else{
                binding.filmProgressBar.visibility = View.GONE
            }
        })
    }

    override fun onItemClick(film: Film) {
        println(film)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}