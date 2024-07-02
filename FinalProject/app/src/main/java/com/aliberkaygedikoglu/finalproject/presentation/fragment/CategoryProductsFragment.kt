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
import com.aliberkaygedikoglu.finalproject.databinding.FragmentCategoryProductsBinding
import com.aliberkaygedikoglu.finalproject.model.Category
import com.aliberkaygedikoglu.finalproject.model.Products
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.adapter.CategoryProductsAdapter
import com.aliberkaygedikoglu.finalproject.presentation.adapter.MainAdapter
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryProductsFragment : Fragment() {
    lateinit var binding: FragmentCategoryProductsBinding
    lateinit var dummyService: DummyService
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))
        val user = requireActivity().intent.getSerializableExtra("user", User::class.java)

        val category = arguments?.getSerializable("myCategory",Category::class.java)
        Log.d("navCat",category!!.slug)
        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.getCategoryProducts(category.slug).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                val adapter = CategoryProductsAdapter(response.body()!!.products,user!!)
                binding.recyclerCategoryProducts.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.recyclerCategoryProducts.adapter=adapter
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


        return binding.root
    }


}