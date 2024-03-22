package com.example.foodappmvvm_retrofit.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodappmvvm_retrofit.MainActivity
import com.example.foodappmvvm_retrofit.R
import com.example.foodappmvvm_retrofit.activites.CategoryMealsActivity

import com.example.foodappmvvm_retrofit.activites.MealActivity
import com.example.foodappmvvm_retrofit.adapter.CategorisAdapter
import com.example.foodappmvvm_retrofit.adapter.MostPopularAdapter
import com.example.foodappmvvm_retrofit.databinding.FragmentHomeBinding
import com.example.foodappmvvm_retrofit.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodappmvvm_retrofit.model.Category
import com.example.foodappmvvm_retrofit.model.MealsByCategory
import com.example.foodappmvvm_retrofit.model.Meal
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategorisAdapter


    companion object{
        const val MEAL_ID="com.example.foodappmvvm_retrofit.fragments.idMeal"
        const val MEAL_NAME="com.example.foodappmvvm_retrofit.fragments.nameMeal"
        const val MEAL_THUMB="com.example.foodappmvvm_retrofit.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.foodappmvvm_retrofit.fragments.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel=(activity as MainActivity).viewModel
        popularItemAdapter= MostPopularAdapter()
        categoriesAdapter= CategorisAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomMeals()
        observeRandomMeals()
        onRandomMealClick()
        viewModel.getPopularItems()
        observePopularItemLiveData()
        prepPopularItemRecyclerview()
        onPopularItemClick()
        prepareCategoryReyclerview()
        onPopularItemLongClick()

        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()
        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            replaceFragment(SearchFragment())
        }
    }

    private fun onPopularItemLongClick() {
        popularItemAdapter.onLongItemClick={meal->
            val mealBottomSheetFragment=MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")


        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category ->
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)// STOPSHIP:
            startActivity(intent)
            
        }
    }

    private fun prepareCategoryReyclerview() {
        binding.rvViewCategory.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer{Categories->

                categoriesAdapter.setCategoryList(Categories)


        })
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick={  meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)


        }
    }

    private fun prepPopularItemRecyclerview() {
        binding.rvViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularItemAdapter

        }
    }

    private fun observePopularItemLiveData() {
        viewModel.observePupularItemsLiveData().observe(viewLifecycleOwner, object : Observer<List<MealsByCategory>> {
            override fun onChanged(value: List<MealsByCategory>) {
                val mealList = value
                popularItemAdapter.setMeals(mealList as ArrayList<MealsByCategory>)
            }
        })
    }

    private fun onRandomMealClick() {
        binding.cvRandomMeal.setOnClickListener{
            val intent= Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeals() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,object:Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment).load(value.strMealThumb).into(binding.imgRandomMeal)
                randomMeal=value
            }

        })
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.host_fragment, fragment).commit()
    }


}