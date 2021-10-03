package com.example.ecommapp.onboard_activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.example.ecommapp.R
import com.example.ecommapp.user_auth.UserAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.onbaord_layout.*

@AndroidEntryPoint
class OnboardActivity : AppCompatActivity() {

    lateinit var sliderAdapter : OnboardViewPagerAdapter
    val dots : MutableList<TextView> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onbaord_layout)
        // Disable Night Mode, Doesn't work on XIAOMI Devices
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Hide Status Bar
        /*@Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                         WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
        }*/

        window.statusBarColor = Color.parseColor("#fe5c45")
        window.navigationBarColor = Color.parseColor("#fe5c45")

        sliderAdapter = OnboardViewPagerAdapter(this)
        slider.adapter = sliderAdapter
        addDots(0)

        slider.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                addDots(position)
                addGettingStartedButton(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        skip_btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@OnboardActivity, UserAuth::class.java)
                startActivity( intent )
                finish()
            }
        })

        get_started_btn_changed.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@OnboardActivity, UserAuth::class.java)
                startActivity( intent )
                finish()
            }
        })
    }

    fun addDots(position: Int){
        //&#8857;  &#8226;
        //val my_dots : MutableList<TextView> = mutableListOf()
        dots_layout.removeAllViews()
        for( i in 0..NUMBER_OF_PAGES-1 ){
            //dots.get(i).setText(Html.fromHtml("&#8226;"))
            //dots.get(i).setTextSize(35.0F)
            val tv : TextView = TextView(this)
            if(i == position){
                tv.setText( Html.fromHtml("&#8857;") )

            }
            else
                tv.setText( Html.fromHtml("&#8226;") )

            tv.setTextColor( Color.parseColor("#FFFFFF") )
            tv.setTextSize(25F)
            dots_layout.addView(tv)
        }

    }
    fun addGettingStartedButton(position : Int){
        //This function is used to check if we are on the last page and remove the skip button to add the getting started button instead

        if(position == NUMBER_OF_PAGES -1 )
        {
            val skip_btn_animation : Animation
            skip_btn_animation = AnimationUtils.loadAnimation(this, R.anim.skip_btn_fade_out)
            skip_btn_animation.duration = 0
            skip_btn.animation = skip_btn_animation

            skip_btn.visibility = View.GONE
            skip_btn.isClickable = false


            val get_started_btn_animation : Animation
            get_started_btn_animation = AnimationUtils.loadAnimation(this, R.anim.getting_started_button_from_left)
            get_started_btn_animation.duration = 800
            get_started_btn_changed.animation = get_started_btn_animation

            get_started_btn_changed.visibility = View.VISIBLE
        }
        else{
            if(skip_btn.visibility == View.GONE &&  get_started_btn_changed.visibility == View.VISIBLE){

                skip_btn.isClickable = true
                skip_btn.visibility = View. VISIBLE

                val skip_btn_animation : Animation
                skip_btn_animation = AnimationUtils.loadAnimation(this, R.anim.skip_btn_fade_in)
                skip_btn_animation.duration = 2500
                skip_btn.animation = skip_btn_animation


                val get_started_btn_animation : Animation
                get_started_btn_animation = AnimationUtils.loadAnimation(this, R.anim.getting_started_button_from_right)
                get_started_btn_animation.duration = 1500
                get_started_btn_changed.animation = get_started_btn_animation

                get_started_btn_changed.visibility = View.GONE
            }
        }
    }

}