package com.aliberkaygedikoglu.finalproject.presentation.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.FragmentCategoriesBinding
import com.aliberkaygedikoglu.finalproject.model.Category
import com.aliberkaygedikoglu.finalproject.model.Products
import com.aliberkaygedikoglu.finalproject.presentation.adapter.CategoriesAdapter
import com.aliberkaygedikoglu.finalproject.presentation.adapter.MainAdapter
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var dummyService: DummyService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater,container,false)

        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))

        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.getCategories().enqueue(object : Callback<List<Category>>{
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>
            ) {
                Log.d("categ",response.body()!!.toString())

                val adapter = CategoriesAdapter(response.body()!!)
                binding.recycylerCategories.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.recycylerCategories.adapter=adapter
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }


}