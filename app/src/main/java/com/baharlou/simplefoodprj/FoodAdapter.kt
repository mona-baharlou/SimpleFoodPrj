package com.baharlou.simplefoodprj

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baharlou.simplefoodprj.databinding.ItemFoodBinding
import com.baharlou.simplefoodprj.room.Food
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(
    private val data: ArrayList<Food>,
    private val context: Context,
    private val foodEvent: FoodEvent
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    //use viewBinding
    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {

            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtDistance.text = "${data[position].txtDistance} miles from you"
            binding.itemTxtPrice.text = "$${data[position].txtPrice} vip"
            binding.itemTxtRating.text = "( " + data[position].numOfRating.toString() + " ratings )"
            binding.itemRatingMain.rating = data[position].rating

            Glide
                .with(context)
                .load(data[position].imgUrl)
                .transform(RoundedCornersTransformation(16, 4))
                .into(binding.itemImgMain)

            itemView.setOnClickListener {
                foodEvent.onFoodClicked(data[adapterPosition], adapterPosition)
            }

            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClicked(data[adapterPosition], adapterPosition)
                true
            }
        }
    }

    /*inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                foodEvent.onFoodClicked(data[adapterPosition], adapterPosition)
            }

            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClicked(data[adapterPosition], adapterPosition)
                true
            }
        }
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        //return FoodViewHolder(view)
//185.51.200.2, 178.22.122.100
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        try {
            return data.size
        } catch (ex: Exception) {
            return 0
        }
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

    fun updateFood(newFood: Food, position: Int) {
        data[position] = newFood
        notifyItemChanged(position)
    }

    fun setData(newList: ArrayList<Food>) {
        //set new data to list
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvent {
        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food: Food, position: Int)
    }

}