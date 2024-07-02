package com.aliberkaygedikoglu.finalproject.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.FragmentMainBinding
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.model.Products
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.adapter.MainAdapter
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var dummyService: DummyService

    lateinit var arr: List<Product>
    override fun onStart() {
        super.onStart()
        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val user = requireActivity().intent.getSerializableExtra("user", User::class.java)


        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.getProducts().enqueue(object :Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                arr = response.body()!!.products
                val adapter = MainAdapter(arr,user!!)
                binding.recyclerMain.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.recyclerMain.adapter=adapter
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {

            }
        })


        return binding.root
    }





}