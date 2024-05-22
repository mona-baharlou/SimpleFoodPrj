package com.baharlou.simplefoodprj.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_food")
data class Food(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val txtSubject: String,
    val txtPrice: String,
    val txtDistance: String,
    val txtCity: String,

    //@ColumnInfo("url") //change name of imgurl in room
    val imgUrl: String,

    val numOfRating: Int,
    val rating: Float
)