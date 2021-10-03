package com.example.ecommapp.user_auth.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommapp.R
import com.example.ecommapp.main_activity.MainActivity
import com.example.ecommapp.user_auth.data.LoginRequest
import com.example.ecommapp.user_auth.data.LoginResponse
import com.example.ecommapp.user_auth.retrofit.LoginAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_toast.view.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.login_layout.password
import kotlinx.android.synthetic.main.onbaord_layout.*
import kotlinx.android.synthetic.main.sign_up_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.login_layout) {

    lateinit var token : String

    @Inject
    lateinit var loginAPI : LoginAPI


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signup_tv.apply {
            setOnClickListener{
                val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
                findNavController().navigate(action)
            }
        }



        login_btn.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {

                var error : Boolean = false
                if (username.text.toString().trim().length == 0 || username.text.toString().isEmpty() || username.text.toString().isBlank()) {

                    username_tv.setTextColor(Color.parseColor("#FF2000"))
                    error = true
                }
                else
                    username_tv.setTextColor(Color.parseColor("#FF000000"))

                if (password.text.toString().trim().length == 0 || password.text.toString().isEmpty() || password.text.toString().isBlank()) {
                    password_tv.setTextColor(Color.parseColor("#FF2000"))
                    //make_custom_toast(context, "Username field must be filled")
                    //Toast.makeText(context, "Password field must be filled", Toast.LENGTH_SHORT).show();
                    error = true
                }
                else
                    password_tv.setTextColor(Color.parseColor("#FF000000"))

                if(error == true){
                    val login_btn_error_animation : Animation
                    login_btn_error_animation = AnimationUtils.loadAnimation(activity?.applicationContext!!, R.anim.button_error)
                    login_btn_error_animation.duration = 50
                    login_btn.startAnimation(login_btn_error_animation)
                } else {

                    val user = LoginRequest(username.text.toString(), password.text.toString())
                    val call = loginAPI.login_request(user)
                    call.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    token = response.body()?.token.toString()
                                    login_auth_action(token, activity?.applicationContext!!)
                                }
                                Log.d("OnResponse", " Token = $token ", null)
                            } else
                                Log.d("OnResponse", "Not Successful ", null)
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Log.d("OnFailure", "Throwable ", t)
                        }
                    })
                }
            }


        })



        /*if(succ == true){
            val intent = Intent(this@LoginFragment,MainActivity::class.java)
            startActivity( intent )
            finish()
        }*/
    }

    fun login_auth_action(token: String, context: Context){
        if(token != "null" && token != "" ){
            //Toast.makeText(context, "Token isnt null", Toast.LENGTH_SHORT).show();
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            //Using activity finish in the fragment is not the best because it gives the fragment too much authority. But I will leave it like this for now
            activity?.finish()

        }
        else{
                Toast.makeText(context, "Username or password isn't correct.", Toast.LENGTH_SHORT).show();
        }

    }

    /*fun make_custom_toast(context: Context?, txt: String){

        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.custom_toast, null)

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        //toast.setGravity(Gravity.V, 0, 0)

        view.toast_msg.text = txt
        toast.setView(view)
        toast.show()
    }*/
}