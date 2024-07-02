package com.aliberkaygedikoglu.finalproject.model

data class Add (
    val userId: Int,
    var password: String,
    val products: List<AddProduct>
)
data class AddProduct(
    val id:Int,
    val quantity:Int,
)
