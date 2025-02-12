package com.example.foodappmvvm_retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodappmvvm_retrofit.databinding.FragmentFavoritesBinding
import com.example.foodappmvvm_retrofit.databinding.MealItemBinding
import com.example.foodappmvvm_retrofit.model.Meal

class FavoritesMealAdapter:RecyclerView.Adapter<FavoritesMealAdapter.FavoritesMealAdapterViewHolder>() {

    inner class FavoritesMealAdapterViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil=object:DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealAdapterViewHolder {
        return FavoritesMealAdapterViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealAdapterViewHolder, position: Int) {
        val meal=differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMeal.setText(meal.strMeal)
    }
}


