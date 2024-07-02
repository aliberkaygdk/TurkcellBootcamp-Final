package com.aliberkaygedikoglu.finalproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.RecyclerMainItemBinding
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.presentation.fragment.LikedsFragmentDirections
import com.aliberkaygedikoglu.finalproject.presentation.fragment.MainFragmentDirections
import com.aliberkaygedikoglu.finalproject.room.DB
import com.aliberkaygedikoglu.finalproject.room.ProductEntity
import com.bumptech.glide.Glide

class LikedsAdapter(private val list: List<ProductEntity>) : RecyclerView.Adapter<LikedsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedsAdapter.ViewHolder {
        val binding = RecyclerMainItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.isLike.setImageResource(R.drawable.icon_favorite)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedsAdapter.ViewHolder, position: Int) {
        val productEntity = list[position]


        holder.itemBinding.productName.text= productEntity.product.title
        Glide.with(holder.itemView.context).load(productEntity.product.thumbnail).into(holder.itemBinding.imageView)
        holder.itemBinding.textViewItemDesc.text=productEntity.product.description
        holder.itemBinding.textViewFiyat.text = productEntity.product.price.toString()

        holder.itemBinding.cardView.setOnClickListener {

            val bundle = LikedsFragmentDirections.likedsToDetail(productEntity.product).arguments
            bundle.putSerializable("myProduct",productEntity.product)

            it.findNavController().navigate(R.id.likedsToDetail,bundle)
        }
        val database = DB.getDatabase(holder.itemView.context)
        holder.itemBinding.isLike.setOnClickListener {

            if (database != null) {
                if (database.productDao().getProduct(productEntity.uid,productEntity.productId).isNotEmpty()){

                    database.productDao().delete(productEntity)
                    holder.itemBinding.isLike.setImageResource(R.drawable.icon_notfavorite)
                    val arrayList = list as ArrayList
                    arrayList.removeAt(position)
                    notifyDataSetChanged()

                }
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder( val itemBinding: RecyclerMainItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}