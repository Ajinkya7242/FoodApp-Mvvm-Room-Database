package com.example.foodappmvvm_retrofit.viewwmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappmvvm_retrofit.db.MealDatabase
import com.example.foodappmvvm_retrofit.model.Meal
import com.example.foodappmvvm_retrofit.model.MealList
import com.example.foodappmvvm_retrofit.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase:MealDatabase):ViewModel() {


    private var mealDeatailsLiveData=MutableLiveData<Meal>()

    fun getMealDeatails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    mealDeatailsLiveData.value=response.body()!!.meals[0]
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealDetail", "onFailure: ${t.localizedMessage}")
            }

        })
    }

    fun observeMealDetailLiveData():LiveData<Meal>{
        return mealDeatailsLiveData
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}