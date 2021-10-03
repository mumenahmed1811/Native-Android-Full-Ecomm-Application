package com.example.ecommapp.main_activity.shared_files.fragments.cart_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.shared_files.recycler_view_adapters.NewCollectionsItemAdapter
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.product_item_layout.view.*

class CartRecyclerViewAdapter(val btn_listener: CartRecyclerViewAdapter.onDeleteButtonClickListener) : androidx.recyclerview.widget.ListAdapter<Product,CartRecyclerViewAdapter.CartRecyclerViewViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecyclerViewViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartRecyclerViewViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CartRecyclerViewViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class CartRecyclerViewViewHolder(view : View): RecyclerView.ViewHolder(view){

        init {

        }
        private val container = view.container_main

        private val imageView = view.cart_item_image
        private val productName = view.cart_item_title
        private val productNewPrice = view.cart_item_price
        private val productColor = view.cart_item_color
        private val productSize = view.cart_item_size
        private val deleteBtn = view.cart_item_delete_button

        fun bind(product: Product) {

            productName.text = product.title
            productNewPrice.text = product.price
            productColor.text = product.color
            productSize.text = product.size

            //Context of the view
            Glide.with(imageView.context) // Context
                .load(product.image) // Data
                .into(imageView) // View

            deleteBtn.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    val product = getItem(position)
                    if(position != RecyclerView.NO_POSITION) {
                        btn_listener.onItemClick(product)
                    }
                }
            })

        }

    }

    interface onDeleteButtonClickListener{
        fun onItemClick(product: Product)
    }

    class Comparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }
}



