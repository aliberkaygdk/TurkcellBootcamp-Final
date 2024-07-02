package com.aliberkaygedikoglu.finalproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aliberkaygedikoglu.finalproject.R
import com.aliberkaygedikoglu.finalproject.databinding.RecyclerMainItemBinding
import com.aliberkaygedikoglu.finalproject.model.Product
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.presentation.fragment.CategoriesFragmentDirections
import com.aliberkaygedikoglu.finalproject.presentation.fragment.CategoryProductsFragmentDirections
import com.aliberkaygedikoglu.finalproject.presentation.fragment.MainFragmentDirections
import com.aliberkaygedikoglu.finalproject.room.DB
import com.aliberkaygedikoglu.finalproject.room.ProductEntity
import com.bumptech.glide.Glide

class CategoryProductsAdapter(private val list: List<Product>,private val user: User) : RecyclerView.Adapter<CategoryProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductsAdapter.ViewHolder {
        val binding = RecyclerMainItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

            val bundle = CategoryProductsFragmentDirections.catProductToDetail(product).arguments
            bundle.putSerializable("myProduct",product)

            it.findNavController().navigate(R.id.catProductToDetail,bundle)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder( val itemBinding: RecyclerMainItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}