package com.aliberkaygedikoglu.finalproject.retrofit

import com.aliberkaygedikoglu.finalproject.model.Add
import com.aliberkaygedikoglu.finalproject.model.Cart
import com.aliberkaygedikoglu.finalproject.model.Carts
import com.aliberkaygedikoglu.finalproject.model.Category
import com.aliberkaygedikoglu.finalproject.model.Products
import com.aliberkaygedikoglu.finalproject.model.Profile
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.model.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET


import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface DummyService {


    @POST("auth/login")
    fun login(@Body userLogin: UserLogin): Call<User>

    @GET("users/{id}")
    fun getProfile(@Path("id")id:Int):Call<Profile>

    @GET("products")
    fun getProducts():Call<Products>

    @GET("products/categories")
    fun getCategories():Call<List<Category>>

    @GET("products/category/{categoryName}")
    fun getCategoryProducts( @Path("categoryName") category:String):Call<Products>

    @GET("carts/user/{userId}")
    fun getUserCart( @Path("userId") userId:Long):Call<Carts>

    @GET("products/search")
    fun search(@Query("q") query:String):Call<Products>

    @POST("carts/add")
    fun add(@Body add: Add): Call<Cart>
}