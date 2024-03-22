package com.example.foodappmvvm_retrofit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodappmvvm_retrofit.MainActivity
import com.example.foodappmvvm_retrofit.R
import com.example.foodappmvvm_retrofit.adapter.FavoritesMealAdapter
import com.example.foodappmvvm_retrofit.databinding.FragmentSearchBinding
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRecyclerviewAdapter:FavoritesMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepapreRecyclerView()
        binding.imgSearch.setOnClickListener {
            searchMeals()
        }

        var searchJob: Job? = null

        binding.etSearchBox.addTextChangedListener {searchQuery ->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(10)
                viewModel.searchMeals(searchQuery.toString())

            }
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.etSearchBox.text.toString()
        if(searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }



        observeSearchMealLiveData()

    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner,Observer{mealList->
            searchRecyclerviewAdapter.differ.submitList(mealList)
        })
    }

    private fun prepapreRecyclerView() {
        searchRecyclerviewAdapter= FavoritesMealAdapter()
        binding.rvSearchedMeal.apply {
            layoutManager=GridLayoutManager(context,2)
            adapter=searchRecyclerviewAdapter
        }
    }

}