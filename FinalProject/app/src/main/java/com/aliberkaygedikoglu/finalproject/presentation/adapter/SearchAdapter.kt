package com.aliberkaygedikoglu.finalproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.RecyclerMainItemBinding
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.fragment.MainFragmentDirections
import com.aliberkaygedikoglu.finalproject.presentation.fragment.SearchFragmentDirections
import com.aliberkaygedikoglu.finalproject.room.DB
import com.aliberkaygedikoglu.finalproject.room.ProductEntity
import com.bumptech.glide.Glide

class SearchAdapter (private var list: List<Product>,private var user: User) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val binding = RecyclerMainItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        val product = list[position]

        holder.itemBinding.productName.text=product.title
        Glide.with(holder.itemView.context).load(product.thumbnail).into(holder.itemBinding.imageView)
        holder.itemBinding.textViewItemDesc.text=product.description
        holder.itemBinding.textViewFiyat.text = product.price.toString()

        val database = DB.getDatabase(holder.itemView.context)
        val likedProduct= database!!.productDao().getProduct(user.id.toInt(),product.id.toInt())
        if (likedProduct.isNotEmpty()){
            holder.itemBinding.isLike.setImageResource(R.drawable.icon_favorite)
        }else{
            holder.itemBinding.isLike.setImageResource(R.drawable.icon_notfavorite)
        }

        holder.itemBinding.isLike.setOnClickListener {
            val database = DB.getDatabase(holder.itemView.context)
            val productEntity = ProductEntity(product.id.toInt(),user.id.toInt(), product )
            // val user =holder.itemView.context.intent.getSerializableExtra("user", User::class.java)
            if (database != null) {
                if (database.productDao().getProduct(user.id.toInt(),product.id.toInt()).isEmpty()){

                    database.productDao().insert(productEntity)
                    holder.itemBinding.isLike.setImageResource(R.drawable.icon_favorite)
                }else{
                    database.productDao().delete(productEntity)
                    holder.itemBinding.isLike.setImageResource(R.drawable.icon_notfavorite)
                }
            }
        }

        holder.itemBinding.cardView.setOnClickListener {

            val bundle = SearchFragmentDirections.searchToDetail(product).arguments
            bundle.putSerializable("myProduct",product)

            it.findNavController().navigate(R.id.searchToDetail,bundle)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder( val itemBinding: RecyclerMainItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }

}
