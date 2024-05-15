package com.baharlou.simplefoodprj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baharlou.simplefoodprj.databinding.ActivityMainBinding
import com.baharlou.simplefoodprj.databinding.DialogAddNewItemBinding
import com.baharlou.simplefoodprj.databinding.DialogDeleteItemBinding
import java.util.Random

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {

    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodList = setList()

        setListToAdapter(foodList)

        buttonClicks(foodList)


    }

    private fun setList(): ArrayList<Food> {
        return arrayListOf(
            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )
    }

    private fun buttonClicks(foodList: ArrayList<Food>) {

        binding.btnAddNewFood.setOnClickListener {
            addItemToList(foodList)
        }
    }

    private fun addItemToList(foodList: ArrayList<Food>) {
        val dialog = AlertDialog.Builder(this).create()

        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)

        dialog.show()

        dialogBinding.dialogBtnDone.setOnClickListener {
            addItemToRecyclerView(dialogBinding, foodList, dialog)
        }

    }

    private fun addItemToRecyclerView(
        dialogBinding: DialogAddNewItemBinding,
        foodList: ArrayList<Food>, dialog: AlertDialog
    ) {
        if (dialogBinding.dialogEdtName.length() > 0 && dialogBinding.dialogEdtCity.length() > 0 &&
            dialogBinding.dialogEdtPrice.length() > 0 && dialogBinding.dialogEdtDistance.length() > 0
        ) {
            val txtName = dialogBinding.dialogEdtName.text.toString()
            val txtPrice = dialogBinding.dialogEdtPrice.text.toString()
            val txtCity = dialogBinding.dialogEdtCity.text.toString()
            val txtDistance = dialogBinding.dialogEdtDistance.text.toString()

            val txtRatingNum: Int = (1..150).random()
            val ratingBarStart: Float = (1..5).random().toFloat()

            val randomforUrl = (0 until 12).random()
            val picUrl = foodList[randomforUrl].imgUrl

            val newFood =
                Food(txtName, txtPrice, txtDistance, txtCity, picUrl, txtRatingNum, ratingBarStart)


            myAdapter.addFood(newFood)
            dialog.dismiss()
            binding.recyclerMain.scrollToPosition(0)

            //random float number
            /*val min = 0f
            val max = 5f
            val rand = min + Random().nextFloat() * (max - min)*/

        } else {
            Toast.makeText(this, "Please enter reuired values", Toast.LENGTH_SHORT).show()

        }

    }

    private fun setListToAdapter(foodList: ArrayList<Food>) {

        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this, this)

        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )


    }

    override fun onFoodClicked() {

    }

    override fun onFoodLongClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()

        val dialogDelete = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDelete.root)

        dialog.setCancelable(true)
        dialog.show()

        dialogDelete.btnSubmit.setOnClickListener {
            myAdapter.deleteFood(food, position)
            dialog.dismiss()
        }

        dialogDelete.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}