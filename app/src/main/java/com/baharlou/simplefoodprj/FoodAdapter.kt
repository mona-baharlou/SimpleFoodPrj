package com.baharlou.simplefoodprj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter() : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
        val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val rating = itemView.findViewById<RatingBar>(R.id.item_rating_main)
        val txtRating = itemView.findViewById<TextView>(R.id.item_txt_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
    }

}