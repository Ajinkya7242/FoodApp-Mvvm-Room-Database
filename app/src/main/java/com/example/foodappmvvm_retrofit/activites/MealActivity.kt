package com.example.foodappmvvm_retrofit.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodappmvvm_retrofit.R
import com.example.foodappmvvm_retrofit.databinding.ActivityMealBinding
import com.example.foodappmvvm_retrofit.db.MealDatabase
import com.example.foodappmvvm_retrofit.fragments.HomeFragment
import com.example.foodappmvvm_retrofit.model.Meal
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel
import com.example.foodappmvvm_retrofit.viewwmodel.MealViewModel
import com.example.foodappmvvm_retrofit.viewwmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDataBase=MealDatabase.getInstance(this)
        val viewModelFactory=MealViewModelFactory(mealDataBase)
        mealMvvm = ViewModelProvider(this,viewModelFactory  ).get(MealViewModel::class.java)

        getMealInfoFromIntent()
        setInformationInViews()
        loadingCase()
        mealMvvm.getMealDeatails(mealId)
        observeMealDatailsLiveData()
        onYoutubeClick()
        onFavoritesClick()
    }

    private fun onFavoritesClick() {
        binding.btnAddToFav.setOnClickListener{
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.rlYoutube.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal? = null
    private fun observeMealDatailsLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                responseCase()
                val meal=value
                mealToSave=value
                binding.tvCategory.setText("Category : "+meal.strCategory)
                binding.tvArea.setText("Area : "+meal.strArea)
                binding.tvDescription.setText(meal.strInstructions)
                youtubeLink=meal.strYoutube.toString()
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetails)

        binding.collasingToolbar.title=mealName
    }

    private fun getMealInfoFromIntent() {
        val intent=intent
        mealId= intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealName= intent.getStringExtra(HomeFragment.MEAL_NAME).toString()
        mealThumb= intent.getStringExtra(HomeFragment.MEAL_THUMB).toString()
    }

    private fun loadingCase(){
        binding.btnAddToFav.isVisible=false
        binding.llTop.isVisible=false
        binding.tvInstructionTitle.isVisible=false
        binding.progressBar.isVisible=true
        binding.rlYoutube.isVisible=false

    }

    private fun responseCase(){
        binding.btnAddToFav.isVisible=true
        binding.llTop.isVisible=true
        binding.tvInstructionTitle.isVisible=true
        binding.progressBar.isVisible=false
        binding.rlYoutube.isVisible=true
    }
}