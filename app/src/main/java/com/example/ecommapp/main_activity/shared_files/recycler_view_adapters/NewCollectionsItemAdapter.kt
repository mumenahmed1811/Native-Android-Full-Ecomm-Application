package com.example.ecommapp.main_activity.shared_files.recycler_view_adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.data.Product
import kotlinx.android.synthetic.main.product_item_layout.view.*

class NewCollectionsItemAdapter(val listener: NewCollectionsItemAdapter.onItemClickListener) : ListAdapter<Product, NewCollectionsItemAdapter.NewCollectionsItemAdapterHolder>(Comparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCollectionsItemAdapterHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false)
        return NewCollectionsItemAdapterHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewCollectionsItemAdapterHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class NewCollectionsItemAdapterHolder(view : View): RecyclerView.ViewHolder(view){

        init {
            view.setOnClickListener{
                val position = adapterPosition
                val product = getItem(position)
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemClick(product)
                }
            }

        }

        private val container = view.container_main

        private val imageView = view.img_product_item_not_card
        private val productName = view.tv_product_item_label
        private val productNewPrice = view.tv_product_item_new_price
        private val productOldPrice = view.tv_product_item_old_price

        fun bind(product: Product) {

            productName.text = product.title
            productNewPrice.text = product.price

            /*var temp = (product.price as Double ) * 1.5
            var oldPrice = temp as String

            productOldPrice.text = oldPrice
            productOldPrice.paintFlags = productOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG*/


            //Context of the view
            Glide.with(imageView.context) // Context
                .load(product.image) // Data
                .into(imageView) // View

        }

    }

    interface onItemClickListener{
        fun onItemClick(product : Product)
    }

    class Comparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }
}

