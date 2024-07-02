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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.FragmentOrdersBinding
import com.aliberkaygedikoglu.finalproject.model.Cart
import com.aliberkaygedikoglu.finalproject.model.Carts
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.adapter.MainAdapter
import com.aliberkaygedikoglu.finalproject.presentation.adapter.OrdersAdapter
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrdersFragment : Fragment() {

    lateinit var binding:FragmentOrdersBinding
    lateinit var dummyService:DummyService

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater,container,false)

        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))


        val intent = activity?.intent?.getSerializableExtra("user", User::class.java)
        Log.d("userIntent", intent!!.id.toString())
        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.getUserCart(intent.id).enqueue(object :Callback<Carts>{
            override fun onResponse(call: Call<Carts>, response: Response<Carts>) {
                if (response.isSuccessful){
                    if (response.body()!!.carts.isNotEmpty()){
                        val arr = response.body()!!.carts[0]
                        Log.d("succes",arr.products.toString())
                        val adapter = OrdersAdapter(arr.products)
                        binding.recyclerOrders.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                        binding.recyclerOrders.adapter = adapter
                        binding.textViewTotal.text = arr.discountedTotal.toString()
                        binding.buttonOrder.visibility = View.VISIBLE
                    }else{
                        binding.textViewEmpty.visibility= View.VISIBLE

                    }

                    binding.buttonOrder.setOnClickListener {
                        Snackbar.make(it, "Gönderildi", Snackbar.LENGTH_LONG).show()
                        Navigation.findNavController(it).navigate(R.id.orderToMain)

                    }

                }
            }

            override fun onFailure(call: Call<Carts>, t: Throwable) {
                Log.e("failed","order hata")
            }
        })


        return binding.root
    }


}