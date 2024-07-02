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
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.FragmentMainBinding
import com.aliberkaygedikoglu.finalproject.databinding.FragmentProfileBinding
import com.aliberkaygedikoglu.finalproject.model.Profile
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var dummyService: DummyService
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val color = sharedPreferences.getString("color","#ffffff")
        binding.layout.setBackgroundColor(Color.parseColor(color))

        val user = activity?.intent?.getSerializableExtra("user",User::class.java)
        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.getProfile(user!!.id.toInt()).enqueue(object :Callback<Profile>{
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful){

                    val profile = response.body()!!
                    binding.userName.text = "User: ${profile.firstName} ${profile.maidenName}"
                    Glide.with(requireActivity()).load(profile.image).into(binding.imageViewProfile)
                    binding.age.text = "Age: ${profile.age}"
                    binding.gender.text = "Gender: ${profile.gender}"
                    binding.email.text = "Email: ${profile.email}"
                    binding.phone.text = "Phone: ${profile.phone}"
                    binding.username.text = "Username: ${profile.username}"
                    binding.birthdate.text = "Birth Date: ${profile.birthDate}"
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
            }
        })








        return binding.root
    }
}