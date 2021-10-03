package com.example.ecommapp.onboard_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.ecommapp.R
import kotlinx.android.synthetic.main.onboard_item.view.*


val NUMBER_OF_PAGES = 3

class OnboardViewPagerAdapter(
    val context : Context
) : PagerAdapter() {


    val images : MutableList<Int>  = mutableListOf()
    val headers : MutableList<Int> = mutableListOf()
    val descriptions : MutableList<Int> = mutableListOf()



    val layoutInflater : LayoutInflater

    init {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        images.add(R.drawable.dilevery_sim)
        images.add(R.drawable.payments_sim)
        images.add(R.drawable.money_back_sim)

        headers.add(R.string.onboarding_header_3)
        headers.add(R.string.onboarding_header_2)
        headers.add(R.string.onboarding_header_1)

        descriptions.add(R.string.onboard_desc)
        descriptions.add(R.string.onboard_desc)
        descriptions.add(R.string.onboard_desc)


    }


    override fun getCount(): Int {
        return NUMBER_OF_PAGES
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view : View = layoutInflater.inflate(R.layout.onboard_item, container, false)

        val item_image = view.slider_image
        val item_header = view.header_title
        val item_desc = view.description


        /*Glide.with(item_image.context) // Context
            .load(images.get(position)) // Data
            .asBitmap()
            .into(item_image)*/
        item_image.setImageResource(images[position])


        item_header.setText(headers.get(position))

        item_desc.setText(descriptions.get(position))


        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView( `object` as ConstraintLayout )
    }




}