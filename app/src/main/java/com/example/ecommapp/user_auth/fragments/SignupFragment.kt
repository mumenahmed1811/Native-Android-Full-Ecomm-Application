package com.example.ecommapp.user_auth.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.MainActivity
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.sign_up_layout.*
import kotlinx.android.synthetic.main.sign_up_layout.password

class SignupFragment :Fragment(R.layout.sign_up_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_tv.apply {
            setOnClickListener{
                val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }

        signup_btn.setOnClickListener(object :View.OnClickListener{

            override fun onClick(v: View?) {
                var error : Boolean = false
                if (name.text.toString().trim().length == 0 || name.text.toString().isEmpty() || name.text.toString().isBlank()) {

                    name_tv.setTextColor(Color.parseColor("#FF2000"))
                    error = true
                }
                else
                    name_tv.setTextColor(Color.parseColor("#FF000000"))

                if (email.text.toString().trim().length == 0 || email.text.toString().isEmpty() || email.text.toString().isBlank()) {
                    email_tv.setTextColor(Color.parseColor("#FF2000"))
                    error = true
                }
                else
                    email_tv.setTextColor(Color.parseColor("#FF000000"))

                if (password.text.toString().trim().length == 0 || password.text.toString().isEmpty() || password.text.toString().isBlank()) {
                    signup_password_tv.setTextColor(Color.parseColor("#FF2000"))
                    error = true
                }
                else
                    signup_password_tv.setTextColor(Color.parseColor("#FF000000"))

                if(error == true){
                    val signup_btn_error_animation : Animation
                    signup_btn_error_animation = AnimationUtils.loadAnimation(activity?.applicationContext!!, R.anim.button_error)
                    signup_btn_error_animation.duration = 50
                    signup_btn.startAnimation(signup_btn_error_animation)
                } else {

                    signup_auth_action(activity?.applicationContext!!)
                }
            }

        })
    }

    fun signup_auth_action(context: Context){

        Toast.makeText(context, "Signed up Successfully ", Toast.LENGTH_SHORT).show()
        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
        findNavController().navigate(action)

    }
}