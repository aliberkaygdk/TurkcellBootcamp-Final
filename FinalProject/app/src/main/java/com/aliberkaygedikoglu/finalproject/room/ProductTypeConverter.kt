package com.aliberkaygedikoglu.finalproject.room

import androidx.room.TypeConverter
import com.aliberkaygedikoglu.finalproject.model.Dimensions
import com.aliberkaygedikoglu.finalproject.model.Meta
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductTypeConverter {
    @TypeConverter
    fun fromProduct(value: Product):String = Gson().toJson(value)

    @TypeConverter
    fun toProduct(value: String):Product = Gson().fromJson(value,Product::class.java)

    @TypeConverter
    fun fromListProduct(value: List<Product>?): String = Gson().toJson(value)

    @TypeConverter
    fun toListProduct(value: String) = Gson().fromJson(value,Array<Product>::class.java).toList()

    @TypeConverter
    fun fromListString(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromDimensions(value: Dimensions):String = Gson().toJson(value)

    @TypeConverter
    fun toDimensions(value: String):Dimensions = Gson().fromJson(value,Dimensions::class.java)

    @TypeConverter
    fun fromListReviews(value: List<Review>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toListReviews(value: String): List<Review> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromMeta(value: Meta):String = Gson().toJson(value)

    @TypeConverter
    fun toMeta(value: String):Meta = Gson().fromJson(value,Meta::class.java)
}