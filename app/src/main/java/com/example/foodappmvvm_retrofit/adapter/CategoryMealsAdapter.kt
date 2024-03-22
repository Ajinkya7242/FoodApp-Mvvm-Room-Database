package com.example.foodappmvvm_retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappmvvm_retrofit.databinding.ActivityCategoryMealsBinding
import com.example.foodappmvvm_retrofit.databinding.MealItemBinding
import com.example.foodappmvvm_retrofit.model.MealList
import com.example.foodappmvvm_retrofit.model.MealsByCategory
import com.example.foodappmvvm_retrofit.viewwmodel.CategoryMealsViewModel

class CategoryMealsAdapter :RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealViewHolder>(){

    private var mealList=ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory)->Unit)

    fun setMealList(mealList: List<MealsByCategory>) {
        this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMeal.text=mealList[position].strMeal

            holder.itemView.setOnClickListener{
                onItemClick.invoke(mealList[position])
            }

    }


}