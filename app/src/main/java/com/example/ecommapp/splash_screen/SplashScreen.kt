package com.example.ecommapp.splash_screen

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ecommapp.R
import com.example.ecommapp.UserPreferences
import kotlinx.android.synthetic.main.splash_screen_layout.*
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.ecommapp.onboard_activity.OnboardActivity
import com.example.ecommapp.user_auth.UserAuth
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    var is_first_time: Boolean? = true

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)

        val userPreferences = UserPreferences(this)
        userPreferences.first_time.asLiveData().observe(this, Observer {
            is_first_time = it
        })

        window.statusBarColor = Color.parseColor("#fe5c45")
        window.navigationBarColor = Color.parseColor("#fe5c45")


        // Animations
        val splash_screen_title_animation : Animation
        splash_screen_title_animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_title_anim)
        splash_screen_title_animation.duration = 2250
        tv_splash_screen_title.animation = splash_screen_title_animation

        val splash_screen_icon_animation : Animation
        splash_screen_icon_animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_icon_anim)
        splash_screen_icon_animation.duration = 2250
        splash_screen_icon.animation = splash_screen_icon_animation

        Handler(Looper.getMainLooper()).postDelayed({

               if(is_first_time!!){

                   lifecycleScope.launch {
                       userPreferences.trigger_first_time(false)
                   }
                   val intent = Intent(this@SplashScreen, OnboardActivity::class.java)
                   startActivity( intent )
                   finish()
               }else
               {
                   val intent = Intent(this@SplashScreen, UserAuth::class.java)
                   startActivity( intent )
                   finish()
               }

        },4000)
    }
}