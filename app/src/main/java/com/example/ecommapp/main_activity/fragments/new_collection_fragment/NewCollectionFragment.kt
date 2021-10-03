package com.example.ecommapp.main_activity.fragments.new_collection_fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.shared_files.recycler_view_adapters.NewCollectionsItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.new_collections_fragment_layout.*

@AndroidEntryPoint
class NewCollectionFragment: Fragment(R.layout.new_collections_fragment_layout), NewCollectionsItemAdapter.onItemClickListener {


    lateinit var new_collection_list : LiveData<List<Product>>

    val new_collections_adapter = NewCollectionsItemAdapter(this)

    val viewModel : NewCollectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_new_collections_main_layout.adapter = new_collections_adapter
        rv_new_collections_main_layout.layoutManager = GridLayoutManager(
            activity?.applicationContext,
            2,
            RecyclerView.VERTICAL, // orientation
            false
        )
        rv_new_collections_main_layout.setHasFixedSize(true)

        new_collection_list = viewModel.new_collection_list_main


        Handler(Looper.getMainLooper()).postDelayed({


            set_data()

            sfl_new_collection_layout.visibility = View.INVISIBLE
            rv_new_collections_main_layout.visibility = View.VISIBLE


        },750)


        // To make sure that data is rendered
        Handler(Looper.getMainLooper()).postDelayed({
                            set_data()
        },200)
    }

    override fun onItemClick(product: Product) {
        val action = NewCollectionFragmentDirections.actionNewCollectionFragmentToProductFragment(product, product.title, R.id.newCollectionFragment)
        findNavController().navigate(action)
    }

    fun set_data(){


        new_collection_list.observe(viewLifecycleOwner, Observer {

            new_collections_adapter.submitList(it)
            new_collections_adapter.notifyDataSetChanged()
        })
    }
}