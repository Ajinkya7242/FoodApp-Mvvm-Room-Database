package com.example.foodappmvvm_retrofit

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodappmvvm_retrofit.databinding.ActivityMainBinding
import com.example.foodappmvvm_retrofit.db.MealDatabase
import com.example.foodappmvvm_retrofit.fragments.CategoryFragment
import com.example.foodappmvvm_retrofit.fragments.FavoritesFragment
import com.example.foodappmvvm_retrofit.fragments.HomeFragment
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModel
import com.example.foodappmvvm_retrofit.viewwmodel.HomeViewModelFactory
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var tts: TextToSpeech
    val viewModel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeviewModelProviderFactory=HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeviewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.btmNavigation.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> replaceFragment(HomeFragment())
                    1 -> replaceFragment(FavoritesFragment())
                    2 -> replaceFragment(CategoryFragment())
                    else -> {
                    }
                }
            }

            override fun onTabReselected(i: Int, tab: AnimatedBottomBar.Tab) {
            }
        })

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.host_fragment, fragment).commit()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.host_fragment)

        if (currentFragment !is HomeFragment) {
            replaceFragment(HomeFragment())
            binding.btmNavigation.selectTabAt(0, true)
        } else {
            super.onBackPressed()
        }
    }


}