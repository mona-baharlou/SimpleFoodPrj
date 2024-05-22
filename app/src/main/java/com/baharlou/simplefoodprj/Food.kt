package com.baharlou.simplefoodprj

import androidx.room.Entity

@Entity
data class Food(
    val txtSubject:String,
    val txtPrice:String,
    val txtDistance:String,
    val txtCity:String,
    val imgUrl:String,
    val numOfRating:Int,
    val rating:Float
)