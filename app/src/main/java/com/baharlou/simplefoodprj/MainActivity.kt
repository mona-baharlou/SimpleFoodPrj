package com.baharlou.simplefoodprj

import android.R.attr.value
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baharlou.simplefoodprj.databinding.ActivityMainBinding
import com.baharlou.simplefoodprj.databinding.DialogAddNewItemBinding
import com.baharlou.simplefoodprj.databinding.DialogDeleteItemBinding
import com.baharlou.simplefoodprj.databinding.DialogUpdateItemBinding
import com.baharlou.simplefoodprj.room.Food
import com.baharlou.simplefoodprj.room.FoodDao
import com.baharlou.simplefoodprj.room.FoodDatabase


class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter
    private lateinit var foodDao: FoodDao
    private lateinit var sharedPref: SharedPreferences
    private lateinit var foodList: List<Food>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodDao = FoodDatabase.getDatabase(this).foodDao

        sharedPref = getSharedPreferences("food", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("firstRun", true)) {
            firstRun()
            sharedPref.edit().putBoolean("firstRun", false).apply()
            //showAllData()
        }

        showAllData()


        // val foodList = createList()

        // setListToAdapter(foodList)

        //buttonClicks(foodList)
        buttonClicks()


    }

    private fun firstRun() {

        val list = arrayListOf(
            Food(
                txtSubject = "Hamburger",
                txtPrice = "15",
                txtDistance = "3",
                txtCity = "Isfahan, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                numOfRating = 20,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled fish",
                txtPrice = "20",
                txtDistance = "2.1",
                txtCity = "Tehran, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                numOfRating = 10,
                rating = 4f
            ),
            Food(
                txtSubject = "Lasania",
                txtPrice = "40",
                txtDistance = "1.4",
                txtCity = "Isfahan, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                numOfRating = 30,
                rating = 2f
            ),
            Food(
                txtSubject = "pizza",
                txtPrice = "10",
                txtDistance = "2.5",
                txtCity = "Zahedan, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                numOfRating = 80,
                rating = 1.5f
            ),
            Food(
                txtSubject = "Sushi",
                txtPrice = "20",
                txtDistance = "3.2",
                txtCity = "Mashhad, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                numOfRating = 200,
                rating = 3f
            ),
            Food(
                txtSubject = "Roasted Fish",
                txtPrice = "40",
                txtDistance = "3.7",
                txtCity = "Jolfa, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                numOfRating = 50,
                rating = 3.5f
            ),
            Food(
                txtSubject = "Fried chicken",
                txtPrice = "70",
                txtDistance = "3.5",
                txtCity = "NewYork, USA",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                numOfRating = 70,
                rating = 2.5f
            ),
            Food(
                txtSubject = "Vegetable salad",
                txtPrice = "12",
                txtDistance = "3.6",
                txtCity = "Berlin, Germany",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                numOfRating = 40,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Grilled chicken",
                txtPrice = "10",
                txtDistance = "3.7",
                txtCity = "Beijing, China",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                numOfRating = 15,
                rating = 5f
            ),
            Food(
                txtSubject = "Baryooni",
                txtPrice = "16",
                txtDistance = "10",
                txtCity = "Ilam, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                numOfRating = 28,
                rating = 4.5f
            ),
            Food(
                txtSubject = "Ghorme Sabzi",
                txtPrice = "11.5",
                txtDistance = "7.5",
                txtCity = "Karaj, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                numOfRating = 27,
                rating = 5f
            ),
            Food(
                txtSubject = "Rice",
                txtPrice = "12.5",
                txtDistance = "2.4",
                txtCity = "Shiraz, Iran",
                imgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                numOfRating = 35,
                rating = 2.5f
            )
        )
        try {
            Thread {
                foodDao.insertAllFood(list)
            }
                .start()
        } catch (ex: Exception) {
            Toast.makeText(this, "${ex.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAllData() {
        try {
            val t = Thread {
                foodList = foodDao.getAllFoods()
            }
            t.start()
            t.join()
            setListToAdapter(foodList)

        } catch (ex: Exception) {
            Toast.makeText(this, "${ex.message}", Toast.LENGTH_SHORT).show()
        }
    }


    //private fun buttonClicks(foodList: ArrayList<Food>) {
    private fun buttonClicks() {

        binding.btnRemoveAll.setOnClickListener {
            try {
                removeAllFoods()
                showAllData()
            } catch (ex: Exception) {
                Toast.makeText(this, "BTN_CLICKED : ${ex.message}", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnAddNewFood.setOnClickListener {
            addItemToList()
        }

        binding.edtSearch.addTextChangedListener {
            searchFood(it)
        }

    }

    private fun removeAllFoods() {
        try {
            val t = Thread {
                foodDao.deleteAllData()
            }
            t.start()
            t.join()
        } catch (ex: Exception) {
            Toast.makeText(this, "DELETE: ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun searchFood(searchText: Editable?) {
        if (searchText!!.isNotEmpty()) {

            try {
                val t = Thread {
                    foodList = foodDao.searchFood(searchText.toString())
                }

                t.start() // spawn thread
                t.join()// wait for thread to finish
                myAdapter.setData(ArrayList(foodList))

            } catch (ex: Exception) {
            }
            //filter data
            /*val cloneList = createList().clone() as ArrayList<Food>
            val filteredList = cloneList.filter { foodItem ->
                foodItem.txtSubject.contains(searchText)
            }
            myAdapter.setData(ArrayList(filteredList))*/

        } else {
            //show all data
            val ta = Thread {
                foodList = foodDao.getAllFoods()
            }
            ta.start()
            ta.join()
            myAdapter.setData(foodList as ArrayList<Food>)
            //myAdapter.setData(createList().clone() as ArrayList<Food>)
        }
    }


    private fun addItemToList() {
        val dialog = AlertDialog.Builder(this).create()

        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)

        dialog.show()

        dialogBinding.dialogBtnDone.setOnClickListener {
            addItemToRecyclerView(dialogBinding, dialog)
        }

    }

    private fun addItemToRecyclerView(
        dialogBinding: DialogAddNewItemBinding,
        dialog: AlertDialog
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

            val randomforUrl = (1 until 12).random()
            val picUrl =
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food$randomforUrl.jpg"
            val newFood =
                Food(
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    imgUrl = picUrl,
                    numOfRating = txtRatingNum,
                    rating = ratingBarStart
                )


            myAdapter.addFood(newFood)

            Thread {
                foodDao.insertOrUpdateFood(newFood)
            }.start()

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

    private fun setListToAdapter(foodList: List<Food>) {

        myAdapter = FoodAdapter(ArrayList(foodList), this, this)

        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }


    override fun onFoodClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        //set data to view
        updateDialogBinding.dialogEdtName.setText(food.txtSubject)
        updateDialogBinding.dialogEdtCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtDistance.setText(food.txtDistance)

        updateDialogBinding.dialogBtnCancel.setOnClickListener { dialog.dismiss() }

        updateDialogBinding.dialogUpdateBtnDone.setOnClickListener {
            if (updateDialogBinding.dialogEdtName.length() > 0 && updateDialogBinding.dialogEdtCity.length() > 0 &&
                updateDialogBinding.dialogEdtPrice.length() > 0 && updateDialogBinding.dialogEdtDistance.length() > 0
            ) {

                val txtName = updateDialogBinding.dialogEdtName.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtPrice.text.toString()
                val txtCity = updateDialogBinding.dialogEdtCity.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtDistance.text.toString()

                val newFood = Food(
                    id = food.id,
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    imgUrl = food.imgUrl,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )
                Thread {
                    foodDao.insertOrUpdateFood(newFood)
                }.start()

                myAdapter.updateFood(newFood, position)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    override fun onFoodLongClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()

        val dialogDelete = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDelete.root)

        dialog.setCancelable(true)
        dialog.show()

        dialogDelete.btnSubmit.setOnClickListener {
            myAdapter.deleteFood(food, position)
            Thread {
                foodDao.deleteFood(food)
            }.start()
            dialog.dismiss()
        }

        dialogDelete.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}