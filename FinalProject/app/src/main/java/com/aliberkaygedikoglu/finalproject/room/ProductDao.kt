package com.aliberkaygedikoglu.finalproject.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliberkaygedikoglu.finalproject.model.Product


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity): Long

    @Delete
    fun delete(product: ProductEntity) : Int

    @Query("SELECT * FROM myProducts WHERE uid=:uid")
    fun getProducts(uid:Int): List<ProductEntity>

    @Query("SELECT * FROM myProducts WHERE uid=:uid AND productId=:pid")
    fun getProduct(uid:Int,pid:Int): List<ProductEntity>
}