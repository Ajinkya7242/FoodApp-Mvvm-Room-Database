package com.example.foodappmvvm_retrofit.viewwmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodappmvvm_retrofit.db.MealDatabase

class MealViewModelFactory(private val mealDatabase:MealDatabase):ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}