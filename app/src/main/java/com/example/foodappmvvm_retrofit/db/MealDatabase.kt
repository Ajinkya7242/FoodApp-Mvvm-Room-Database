package com.example.foodappmvvm_retrofit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodappmvvm_retrofit.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase:RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object{
        @Volatile
        var INSTANCE: MealDatabase?=null
        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if(INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context,MealDatabase::class.java,"meal_db").fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }
}