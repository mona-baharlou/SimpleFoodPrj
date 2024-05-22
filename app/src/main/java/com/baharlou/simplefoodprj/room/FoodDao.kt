package com.baharlou.simplefoodprj.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {

    @Insert
    fun insertFood(food: Food)

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM table_food")
    fun deleteAllData()

    @Query("SELECT * FROM table_food")
    fun getAllFoods() : List<Food>




}