package com.example.foodappmvvm_retrofit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappmvvm_retrofit.databinding.MealItemBinding
import com.example.foodappmvvm_retrofit.databinding.PopularItemsBinding
import com.example.foodappmvvm_retrofit.model.MealsByCategory

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealviewHolder>() {

    private var mealsList=ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory)->Unit)
    var onLongItemClick:((MealsByCategory)->Unit)?=null

    fun setMeals(mealList: ArrayList<MealsByCategory>){
        this.mealsList=mealList
        notifyDataSetChanged()
    }

    class PopularMealviewHolder( val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealviewHolder {
        return PopularMealviewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealviewHolder, position: Int) {
        val meal = mealsList[position]
        Log.d("DATAMEALS",meal.toString())
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }
}