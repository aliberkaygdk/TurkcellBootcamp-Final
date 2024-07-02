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
import com.aliberkaygedikoglu.finalproject.databinding.FragmentLikedsBinding
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.adapter.LikedsAdapter
import com.aliberkaygedikoglu.finalproject.presentation.adapter.MainAdapter
import com.aliberkaygedikoglu.finalproject.room.DB


class LikedsFragment : Fragment() {

    lateinit var binding : FragmentLikedsBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikedsBinding.inflate(inflater,container,false)

        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))

        val database = DB.getDatabase(requireContext())
        val user = requireActivity().intent.getSerializableExtra("user", User::class.java)
        val arr = database?.productDao()?.getProducts(user!!.id.toInt())

        val adapter = LikedsAdapter(arr!!)
        binding.recylerLike.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recylerLike.adapter=adapter
        binding.recylerLike.adapter!!.notifyDataSetChanged()

        if (arr.isEmpty()){
            binding.textView.visibility = View.VISIBLE
        }else{
            binding.textView.visibility = View.INVISIBLE
        }



        return binding.root
    }


}