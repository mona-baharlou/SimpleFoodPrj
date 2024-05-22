package com.baharlou.simplefoodprj.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {

    abstract val foodDao: FoodDao

    companion object {
        private var database: FoodDatabase? = null

        //singleton
        fun getDatabase(context: Context): FoodDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "foodDatabase.db"

                ).build()
            }

            return database!!
        }
    }

}

