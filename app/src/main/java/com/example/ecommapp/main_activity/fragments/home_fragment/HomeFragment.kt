package com.example.ecommapp.main_activity.fragments.home_fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.shared_files.recycler_view_adapters.ProductItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment_layout.*


@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.home_fragment_layout), ProductItemAdapter.onItemClickListener {


    lateinit var new_collection_list : LiveData<List<Product>>
    lateinit var best_selling_list : LiveData<List<Product>>

    val new_collections_adapter = ProductItemAdapter(this)
    val best_selling_adapter = ProductItemAdapter(this)

    val viewModel by activityViewModels<HomeViewModel>()

    var rv_nc_scroll_position : Int = 0
    var rv_bs_scroll_position : Int = 0

    var scroll_view_pos_x : Int = 0
    var scroll_view_pos_y : Int = 0

    var first_time = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recycler View Preparations -- /////////////////////////////////
        // New Collection Recycler View Setup
        rv_new_collections.adapter = new_collections_adapter
        rv_new_collections.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_new_collections.setHasFixedSize(true)

        // Best Selling Recycler View Setup
        rv_best_selling.adapter = best_selling_adapter
        rv_best_selling.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_best_selling.setHasFixedSize(true)
        ///////////////////////////////////////
        // Retrieve State - ////////////////////////////////////
        retain_state()
        //////////////////////////////////////////////////

        //new_collection_list = viewModel.new_collection_list
        //best_selling_list = viewModel.best_sellling_list

        set_data_observers()

        // If FirstTime
        if (first_time){
            Handler().postDelayed({

                if (isConnected(activity?.applicationContext)) {

                    layout_new_collection_shimmer.visibility = View.INVISIBLE
                    rv_new_collections.visibility = View.VISIBLE


                    layout_best_selling_shimmer.visibility = View.INVISIBLE
                    rv_best_selling.visibility = View.VISIBLE


                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "No Internet Connection, Swipe to reload.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }


            }, 750)
        }else{
            layout_new_collection_shimmer.visibility = View.INVISIBLE
            rv_new_collections.visibility = View.VISIBLE


            layout_best_selling_shimmer.visibility = View.INVISIBLE
            rv_best_selling.visibility = View.VISIBLE

            //Handler().postDelayed({home_loading_screen.visibility = View.INVISIBLE},2000)
        }


        // Click Listeners

        container_swipe.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                if (isConnected(activity?.applicationContext)) {

                    if (layout_new_collection_shimmer.visibility == View.INVISIBLE) {

                        layout_new_collection_shimmer.visibility = View.INVISIBLE
                        rv_new_collections.visibility = View.VISIBLE


                        layout_best_selling_shimmer.visibility = View.INVISIBLE
                        rv_best_selling.visibility = View.VISIBLE
                    }


                    viewModel.on_swipe_refresh()
                    set_data_observers()
                } else {
                    Toast.makeText(
                        activity?.applicationContext,
                        "No Internet Connection, Swipe to reload.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }


                // Must be added
                container_swipe.isRefreshing = false
            }


        })

        tv_show_all.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val action = HomeFragmentDirections.actionHomeFragmentToNewCollectionFragment()
                findNavController().navigate(action)
            }
        })


        // To make sure that data is rendered
        Handler().postDelayed({
            set_data_observers()
        }, 200)
    }


    override fun onItemClick(product: Product) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(
            product,
            product.title,
            R.id.homeFragment
        )
        findNavController().navigate(action)
    }

    fun set_data_observers(){

        viewModel.get_new_collection_data_without_hilt()
        viewModel.get_best_selling_data()

        viewModel.new_collection_list.observe(viewLifecycleOwner, Observer {

            new_collections_adapter.submitList(it)
            new_collections_adapter.notifyDataSetChanged()
        })

        viewModel.best_sellling_list.observe(viewLifecycleOwner, Observer {
            best_selling_adapter.submitList(it)
            best_selling_adapter.notifyDataSetChanged()
        })


    }

    fun isConnected(ctx: Context?): Boolean {

        val hasInternet: Boolean

        val connectivityManager =
            ctx?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            hasInternet = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            hasInternet = try {
                if (connectivityManager.activeNetworkInfo == null) {
                    false
                } else {
                    connectivityManager.activeNetworkInfo?.isConnected!!
                }
            } catch (e: Exception) {
                false
            }
        }
        return hasInternet}

    fun retain_state(){

        rv_nc_scroll_position = viewModel.new_collection_rv_scroll_position
        rv_bs_scroll_position = viewModel.best_sellings_rv_scroll_position

        scroll_view_pos_x = viewModel.screen_scroll_position_x
        scroll_view_pos_y = viewModel.screen_scroll_position_y

        first_time = viewModel.first_time



        rv_new_collections.scrollToPosition(rv_nc_scroll_position)
        rv_best_selling.scrollToPosition(rv_bs_scroll_position)

        //Lazm kdaaaaaa 34an y4t8l
        sv_home_fragment.post(object : Runnable{
            override fun run() {
                sv_home_fragment.scrollTo(
                    scroll_view_pos_x,
                    scroll_view_pos_y
                )
            }
        })


    }

    override fun onPause() {
        super.onPause()

        viewModel.set_rv_best_sellings_scroll_pos(
            (rv_best_selling.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        )
        viewModel.set_rv_new_collection_scroll_pos(
            (rv_new_collections.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        )
        viewModel.set_scroll_x_pos(sv_home_fragment.scrollX)
        viewModel.set_scroll_y_pos(sv_home_fragment.scrollY)

        viewModel.set_first_time_boolean(false)

    }
}

