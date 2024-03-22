package com.example.foodappmvvm_retrofit.viewwmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodappmvvm_retrofit.model.MealsByCategory
import com.example.foodappmvvm_retrofit.model.MealsByCategoryLists
import com.example.foodappmvvm_retrofit.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {

    val mealsLiveData=MutableLiveData<List<MealsByCategory>>()
        fun getMealsByCategory(categoryName:String){
            RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object :Callback<MealsByCategoryLists>{
                override fun onResponse(
                    call: Call<MealsByCategoryLists>,
                    response: Response<MealsByCategoryLists>
                ) {
                    response.body()?.let {mealList ->
                        mealsLiveData.postValue(mealList.meals)
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryLists>, t: Throwable) {
                    Log.d("CategoryMealsError",t.message.toString())
                }

            })
        }

    fun observeMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}