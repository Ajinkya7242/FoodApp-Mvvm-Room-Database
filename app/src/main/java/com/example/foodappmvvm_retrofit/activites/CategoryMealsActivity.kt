package com.example.foodappmvvm_retrofit.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodappmvvm_retrofit.R
import com.example.foodappmvvm_retrofit.adapter.CategoryMealsAdapter
import com.example.foodappmvvm_retrofit.databinding.ActivityCategoryMealsBinding
import com.example.foodappmvvm_retrofit.fragments.HomeFragment
import com.example.foodappmvvm_retrofit.viewwmodel.CategoryMealsViewModel
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        categoryMealsViewModel= ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer{mealList->
            binding.tvCateforyCount.text=intent.getStringExtra(HomeFragment.CATEGORY_NAME)+" :- "+mealList.size.toString()
            categoryMealsAdapter.setMealList(mealList)

        })

        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoryMealsAdapter.onItemClick={
            val intent= Intent(this,MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
            startActivity(intent)
        }

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}