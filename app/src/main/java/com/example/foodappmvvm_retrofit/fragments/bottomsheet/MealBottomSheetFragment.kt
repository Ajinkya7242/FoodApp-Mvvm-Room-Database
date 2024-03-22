package com.example.foodappmvvm_retrofit.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodappmvvm_retrofit.MainActivity
import com.example.foodappmvvm_retrofit.R
import com.example.foodappmvvm_retrofit.activites.MealActivity
import com.example.foodappmvvm_retrofit.databinding.FragmentMealBottomSheetBinding
import com.example.foodappmvvm_retrofit.fragments.HomeFragment
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }


        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()
        onBottomSheetClickListener()
    }

    private fun onBottomSheetClickListener() {
        binding.bottomsheet.setOnClickListener {
            if(mealName!=null&&mealThumb!=null){
                val bundle=Bundle()
                val intent= Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }

                startActivity(intent)
            }
        }
    }

    private var mealName:String?=null
    private var mealThumb:String?=null

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomsheetMealLiveData().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvBottomsheetMealname.text = meal.strMeal

            mealThumb=meal.strMealThumb
            mealName=meal.strMeal
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) = MealBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(MEAL_ID, param1)
            }
        }
    }
}