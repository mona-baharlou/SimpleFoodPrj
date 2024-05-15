package com.baharlou.simplefoodprj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(
    private val data: ArrayList<Food>,
    private val context: Context,
    private val foodEvent: FoodEvent
) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
        val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_main)
        val txtRating = itemView.findViewById<TextView>(R.id.item_txt_rating)

        fun bindData(position: Int) {

            txtSubject.text = data[position].txtSubject
            txtCity.text = data[position].txtCity
            txtDistance.text = "${data[position].txtDistance} miles from you"
            txtPrice.text = "$${data[position].txtPrice} vip"
            txtRating.text = "( " + data[position].numOfRating.toString() + " ratings )"
            ratingBar.rating = data[position].rating

            Glide
                .with(context)
                .load(data[position].imgUrl)
                .transform(RoundedCornersTransformation(16, 4))
                .into(imgMain)

            itemView.setOnClickListener {
                foodEvent.onFoodClicked()
            }

            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClicked(data[adapterPosition], adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun addFood(newFood: Food) {
        //add food to list
        data.add(0, newFood)
        notifyItemInserted(0)
    }

    fun deleteFood(oldFood: Food, oldPosition: Int) {

        data.remove(oldFood)
        notifyItemRemoved(oldPosition)

    }

    interface FoodEvent {
        fun onFoodClicked()
        fun onFoodLongClicked(food: Food, position: Int)
    }

}