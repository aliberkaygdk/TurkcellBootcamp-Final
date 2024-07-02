package com.aliberkaygedikoglu.finalproject.presentation.fragment

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.FragmentDetailBinding
import com.aliberkaygedikoglu.finalproject.model.Add
import com.aliberkaygedikoglu.finalproject.model.AddProduct
import com.aliberkaygedikoglu.finalproject.model.Cart
import com.aliberkaygedikoglu.finalproject.model.Carts
import com.aliberkaygedikoglu.finalproject.model.Category
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.model.Products
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.adapter.MainAdapter
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.aliberkaygedikoglu.finalproject.room.DB
import com.aliberkaygedikoglu.finalproject.room.ProductEntity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {

    lateinit var binding:FragmentDetailBinding
    private lateinit var dummyService: DummyService
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)

        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))

        val user = requireActivity().intent.getSerializableExtra("user",User::class.java)
        val product = arguments?.getSerializable("myProduct", Product::class.java)
        Glide.with(requireContext()).load(product!!.thumbnail).into(binding.imageViewDetail)
        binding.textViewDetailName.text = product.title
        binding.textViewDetailDescription.text = product.description
        binding.textViewDiscount.text = "Discount Percentage: ${product.discountPercentage}"
        binding.textViewPrice.text = "Price: ${product.price}"
        val price = product.price
        val discount = product.discountPercentage
        val total = (price - price*discount/100)
        val format = String.format("%.2f", total)
        binding.total.text ="Total: $format"

       /* val database = DB.getDatabase(requireContext())
        binding.buttonLike.setOnClickListener {
            val productEntity = ProductEntity(product.id.toInt(),user!!.id.toInt(), product )
           // database!!.productDao().insert(productEntity)
            if (database != null) {
                if (database.productDao().getProduct(user.id.toInt(),product.id.toInt()).isEmpty()){

                    database.productDao().insert(productEntity)
                }else{
                    database.productDao().delete(productEntity)
                }
            }
        }*/

        binding.buttonAdd.setOnClickListener {
            val list= listOf(AddProduct(1,2), AddProduct(2,1))
            val add = Add(user!!.id.toInt(),"${user.username}pass",list)
            dummyService = ApiClient.getClient().create(DummyService::class.java)
            dummyService.add(add).enqueue(object :Callback<Cart>{
                override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                    Log.d("add",response.code().toString())
                }

                override fun onFailure(call: Call<Cart>, t: Throwable) {

                }
            })
        }





        return binding.root
    }


}