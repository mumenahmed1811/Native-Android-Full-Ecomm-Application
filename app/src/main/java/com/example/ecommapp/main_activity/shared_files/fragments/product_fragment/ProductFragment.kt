package com.example.ecommapp.main_activity.shared_files.fragments.product_fragment

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ecommapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_fragment_layout.*

@AndroidEntryPoint
class ProductFragment: Fragment(R.layout.product_fragment_layout) {

    val viewModel by activityViewModels<ProductViewModel>()

    private val args by navArgs<ProductFragmentArgs>()

    var selected_color : String = "Black"
    var selected_size : String = "Large"
    var favorited : Boolean = false

    var context = this@ProductFragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initialize_color_picker_buttons()
        handle_favorite_button()
        handle_color_picker_buttons()
        handle_size_picker_colors()

        Glide.with(img_product_image_not_container.context)
            .load(args.productData.image)
            .into(img_product_image_not_container)


        product_price_tv.setText(args.productData.price)

        rating_bar.numStars = 4

        tv_product_description_label.setText(args.productData.title)

        tv_product_description.setText(args.productData.description)



        btn_add_to_cart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val num = viewModel.insert_item_to_cart(
                    activity?.applicationContext!!
                    ,
                    args.productData.copy(
                        color = selected_color,
                        size = selected_size
                    )
                )
                Log.d(TAG, "Inserted : ${num}")

                if (args.destinationId == R.id.homeFragment) {
                    val action = ProductFragmentDirections.actionProductFragmentToHomeFragment()
                    findNavController().navigate(action)
                } else if (args.destinationId == R.id.newCollectionFragment) {
                    val action =
                        ProductFragmentDirections.actionProductFragmentToNewCollectionFragment()
                    findNavController().navigate(action)
                }
            }
        })

    }

    fun handle_favorite_button(){
        favorite_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(favorited) {
                    img_favorited.setImageResource(R.drawable.ic_unfavorite)
                    favorited = false
                }
                else{
                    img_favorited.setImageResource(R.drawable.ic_favorite)
                    favorited = true
                }

            }
        })
        img_favorited.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(favorited) {
                    img_favorited.setImageResource(R.drawable.ic_unfavorite)
                    favorited = false
                }
                else{
                    img_favorited.setImageResource(R.drawable.ic_favorite)
                    favorited = true
                }
            }
        })

    }
    fun initialize_color_picker_buttons(){
        img_black_color_choosen.visibility = View.VISIBLE


        img_blue_color_choosen.visibility = View.INVISIBLE
        img_cyan_color_choosen.visibility = View.INVISIBLE
        img_tomato_color_choosen.visibility = View.INVISIBLE
    }
    fun handle_color_picker_buttons(){

        btn_color_black.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                img_black_color_choosen.visibility = View.VISIBLE
                selected_color = "Black"

                img_blue_color_choosen.visibility = View.INVISIBLE
                img_cyan_color_choosen.visibility = View.INVISIBLE
                img_tomato_color_choosen.visibility = View.INVISIBLE

            }
        })

        btn_color_blue.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                img_blue_color_choosen.visibility = View.VISIBLE
                selected_color = "Blue"

                img_black_color_choosen.visibility = View.INVISIBLE
                img_cyan_color_choosen.visibility = View.INVISIBLE
                img_tomato_color_choosen.visibility = View.INVISIBLE


            }
        })

        btn_color_cyan.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                img_cyan_color_choosen.visibility = View.VISIBLE
                selected_color = "Cyan"

                img_blue_color_choosen.visibility = View.INVISIBLE
                img_black_color_choosen.visibility = View.INVISIBLE
                img_tomato_color_choosen.visibility = View.INVISIBLE


            }
        })

        btn_color_tomato.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                img_tomato_color_choosen.visibility = View.VISIBLE
                selected_color = "Orange"

                img_blue_color_choosen.visibility = View.INVISIBLE
                img_cyan_color_choosen.visibility = View.INVISIBLE
                img_black_color_choosen.visibility = View.INVISIBLE

            }
        })
    }
    fun handle_size_picker_colors(){

        btn_size_picker_l.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                btn_size_picker_l.setCardBackgroundColor(Color.parseColor("#fe5c45"))
                tv_size_picker_l.setTextColor(Color.parseColor("#FFFFFF"))
                selected_size = "Large"

                btn_size_picker_m.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_m.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_xl.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_xl.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_s.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_s.setTextColor(Color.parseColor("#bcbbc5"))

            }
        })

        btn_size_picker_m.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                btn_size_picker_m.setCardBackgroundColor(Color.parseColor("#fe5c45"))
                tv_size_picker_m.setTextColor(Color.parseColor("#FFFFFF"))
                selected_size = "Medium"

                btn_size_picker_l.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_l.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_xl.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_xl.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_s.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_s.setTextColor(Color.parseColor("#bcbbc5"))

            }
        })

        btn_size_picker_xl.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                btn_size_picker_xl.setCardBackgroundColor(Color.parseColor("#fe5c45"))
                tv_size_picker_xl.setTextColor(Color.parseColor("#FFFFFF"))
                selected_size = "X Large"

                btn_size_picker_m.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_m.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_l.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_l.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_s.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_s.setTextColor(Color.parseColor("#bcbbc5"))

            }
        })

        btn_size_picker_s.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                btn_size_picker_s.setCardBackgroundColor(Color.parseColor("#fe5c45"))
                tv_size_picker_s.setTextColor(Color.parseColor("#FFFFFF"))
                selected_size = "Small"

                btn_size_picker_m.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_m.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_xl.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_xl.setTextColor(Color.parseColor("#bcbbc5"))

                btn_size_picker_l.setCardBackgroundColor(Color.parseColor("#f9f9f9"))
                tv_size_picker_l.setTextColor(Color.parseColor("#bcbbc5"))

            }
        })

    }
}

