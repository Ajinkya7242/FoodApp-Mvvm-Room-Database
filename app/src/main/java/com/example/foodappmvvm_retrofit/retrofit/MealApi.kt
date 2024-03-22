package com.example.foodappmvvm_retrofit.retrofit

import com.example.foodappmvvm_retrofit.model.CategoryList
import com.example.foodappmvvm_retrofit.model.MealsByCategoryLists
import com.example.foodappmvvm_retrofit.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String):Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String):Call<MealsByCategoryLists>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") categoryName:String):Call<MealsByCategoryLists>

    @GET("categories.php")
    fun getCategorys():Call<CategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s")searchQuery: String): Call<MealList>
}