package com.example.ecommapp.main_activity.shared_files.fragments.cart_fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.data.Product
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_fragment_layout.*
import javax.inject.Inject

class CartFragment @Inject constructor(

) : Fragment(R.layout.cart_fragment_layout), CartRecyclerViewAdapter.onDeleteButtonClickListener {


    lateinit var cart_list : List<Product>

    val cart_adapter = CartRecyclerViewAdapter(this)

    val viewModel by activityViewModels<CartFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_cart.adapter = cart_adapter
        rv_cart.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv_cart.setHasFixedSize(true)

        /*cart_list = viewModel.get_all_products(activity?.applicationContext!!)
        cart_adapter.submitList(
            cart_list
        )
        cart_adapter.notifyDataSetChanged()
        Log.d(TAG, "Fetched Data: {${cart_list.get(1).title}}")*/

        /*var p = viewModel.get_first_item(activity?.applicationContext!!, "1")
        cart_adapter.submitList(
            listOf(
                p
            )
        )
        cart_adapter.notifyDataSetChanged()
        var s = p.title
        Log.d(TAG, "Fetched Data: {$s}")*/

        viewModel.get_all_products(activity?.applicationContext!!).observe(viewLifecycleOwner, Observer {
            cart_adapter.submitList(it)
            cart_adapter.notifyDataSetChanged()
        })

        //viewModel.get_first_item(activity?.applicationContext!!)

        //activity?.actionBar?.hide()
        //cart_fragment_toolbar.inflateMenu(R.menu.empty_menu)
    }



    override fun onItemClick(product: Product) {
        viewModel.delete_product(activity?.applicationContext!!, product )
        viewModel.get_all_products(activity?.applicationContext!!).observe(viewLifecycleOwner, Observer {
            cart_adapter.submitList(it)
            cart_adapter.notifyDataSetChanged()
        })
    }
}