package com.baharlou.simplefoodprj.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateFood(food: Food)

    @Insert
    fun insertFood(food: Food)

    @Insert
    fun insertAllFood(data: List<Food>)

    @Update
    fun updateFood(food: Food)

    @Delete
    fun deleteFood(food: Food)

    @Query("DELETE FROM table_food")
    fun deleteAllData()

    @Query("SELECT * FROM table_food")
    fun getAllFoods(): List<Food>

    @Query("SELECT * FROM table_food WHERE txtSubject like '%' || :filter || '%'")
    fun searchFood(filter: String): List<Food>


}