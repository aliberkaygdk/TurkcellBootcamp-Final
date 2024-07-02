package com.aliberkaygedikoglu.finalproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class], version = 9)
@TypeConverters(ProductTypeConverter::class)
abstract class DB : RoomDatabase(){

    abstract fun productDao(): ProductDao

    companion object {
        private var instance: DB? = null

        fun getDatabase(context: Context): DB? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    DB::class.java,
                    "products.db"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

}