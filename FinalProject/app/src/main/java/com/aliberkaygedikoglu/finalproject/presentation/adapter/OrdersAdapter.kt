package com.aliberkaygedikoglu.finalproject.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.RecyclerMainItemBinding
import com.aliberkaygedikoglu.finalproject.model.CartProduct
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.presentation.fragment.MainFragmentDirections

import com.bumptech.glide.Glide

class OrdersAdapter(private val list: List<CartProduct>) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val binding = RecyclerMainItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.isLike.visibility = View.INVISIBLE
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        val product = list[position]

        holder.itemBinding.productName.text=product.title
        Glide.with(holder.itemView.context).load(product.thumbnail).into(holder.itemBinding.imageView)
        holder.itemBinding.textViewItemDesc.text="Quantity: ${product.quantity}"
        holder.itemBinding.textViewFiyat.text = product.discountedTotal.toString()

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder( val itemBinding: RecyclerMainItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}