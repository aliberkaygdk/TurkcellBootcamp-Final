package com.aliberkaygedikoglu.finalproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aliberkaygedikoglu.finalproject.model.Product

@Entity(tableName = "myProducts")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "productId")
    val productId:Int,
    val uid:Int,
    val product: Product
)

