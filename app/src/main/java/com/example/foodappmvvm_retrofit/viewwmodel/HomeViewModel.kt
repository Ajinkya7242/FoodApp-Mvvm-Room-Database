package com.example.foodappmvvm_retrofit.viewwmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappmvvm_retrofit.db.MealDatabase
import com.example.foodappmvvm_retrofit.model.Category
import com.example.foodappmvvm_retrofit.model.CategoryList
import com.example.foodappmvvm_retrofit.model.MealsByCategoryLists
import com.example.foodappmvvm_retrofit.model.MealsByCategory
import com.example.foodappmvvm_retrofit.model.Meal
import com.example.foodappmvvm_retrofit.model.MealList
import com.example.foodappmvvm_retrofit.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealsDatabase: MealDatabase
) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favouriteMealsLiveData = mealsDatabase.mealDao().getAllMeals()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var bottomsheetMealLiveData = MutableLiveData<Meal>()
    private val searchMealsLiveData = MutableLiveData<List<Meal>>()
    fun getRandomMeals() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    Log.d("Rendom Meals", "Rendom Meals = ${randomMeal.idMeal}")
                    Log.d("Rendom Meals img", "Rendom Meals img = ${randomMeal.strMeal}")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment RandomMeal Api Error", t.localizedMessage.toString())
            }

        })

    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryLists> {
                override fun onResponse(
                    call: Call<MealsByCategoryLists>,
                    response: Response<MealsByCategoryLists>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                        Log.d("PopularItemsAPi", "Response-${response.body()}")

                    }
                }

                override fun onFailure(call: Call<MealsByCategoryLists>, t: Throwable) {
                    Log.d("PopularItemsAPi", "Error-${t.message}")
                }

            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategorys().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePupularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoritesMealLiveData(): LiveData<List<Meal>> {
        return favouriteMealsLiveData
    }

    fun observeBottomsheetMealLiveData(): LiveData<Meal> {
        return bottomsheetMealLiveData
    }

    fun getMealById(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                var meal = response.body()?.meals?.first()
                meal?.let { meal ->
                    bottomsheetMealLiveData.postValue(meal)

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("ERROR Bottom sheet", t.localizedMessage)
            }

        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealsDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealsDatabase.mealDao().upsert(meal)
        }
    }

    fun searchMeals(searchQuery: String) =
        RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealsList = response.body()?.meals
                mealsList?.let {
                    searchMealsLiveData.postValue(it)
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("ERROR Search Meal", t.localizedMessage)
            }

        })

    fun observeSearchMealLiveData():LiveData<List<Meal>>{
        return searchMealsLiveData
    }


}