package com.example.foodappmvvm_retrofit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodappmvvm_retrofit.MainActivity
import com.example.foodappmvvm_retrofit.R
import com.example.foodappmvvm_retrofit.activites.CategoryMealsActivity
import com.example.foodappmvvm_retrofit.adapter.CategorisAdapter
import com.example.foodappmvvm_retrofit.adapter.CategoryMealsAdapter
import com.example.foodappmvvm_retrofit.databinding.FragmentCategoryBinding
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel


class CategoryFragment : Fragment() {
    lateinit var binding:FragmentCategoryBinding
    lateinit var categoryAdapter: CategorisAdapter
    lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerview()
        observeCategories()
        onCategoryClick()
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner,Observer{categories->
            categoryAdapter.setCategoryList(categories)

        })
    }

    private fun prepareRecyclerview() {
        categoryAdapter= CategorisAdapter()
        binding.rvCategoryList.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick={category ->
            val intent= Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)// STOPSHIP:
            startActivity(intent)

        }
    }

}