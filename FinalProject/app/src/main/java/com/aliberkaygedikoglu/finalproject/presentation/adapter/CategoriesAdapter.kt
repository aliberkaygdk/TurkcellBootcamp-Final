package com.aliberkaygedikoglu.finalproject.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.RecyclerCategoryItemBinding
import com.aliberkaygedikoglu.finalproject.model.Category
import com.aliberkaygedikoglu.finalproject.model.Products
import com.aliberkaygedikoglu.finalproject.presentation.fragment.CategoriesFragmentDirections
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesAdapter(private val list: List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]
        holder.itemBinding.categoryName.text=category.name
       // Glide.with(holder.itemView.context).load(category.images[0]).into(holder.itemBinding.imageView)

        holder.itemBinding.categoryCard.setOnClickListener{
            // navigate ile veri taşı diğer fragmentte al ve o fragmentte servise çık
            val myCategory = Category(category.slug,category.name,category.url)
            val bundle = CategoriesFragmentDirections.openCategoryProducts(category).arguments
            bundle.putSerializable("myCategory",myCategory)


            it.findNavController().navigate(R.id.openCategoryProducts,bundle)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder( val itemBinding: RecyclerCategoryItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}
