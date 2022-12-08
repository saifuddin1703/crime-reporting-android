package com.example.crimereporting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.crimereporting.retrofit.requestBodies.authentication.LoginVerifyOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SendOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SignupVerifyOTPRequestBody
import com.example.crimereporting.retrofit.responseBodies.authentication.LoginOTPResponse
import com.example.crimereporting.retrofit.responseBodies.authentication.SignupOTPResponse
import com.example.crimereporting.ui.authentication.AuthenticationViewModel
import com.example.crimereporting.ui.authentication.LoginFragment
import com.example.crimereporting.ui.authentication.NameSetupFragment
import com.example.crimereporting.ui.authentication.Signup
import com.example.crimereporting.ui.components.OtpEditText
import com.example.crimereporting.utils.DEFAULT_TOKEN
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    private val authenticationViewModel by viewModels<AuthenticationViewModel>()
    private lateinit var back : ImageView
    private lateinit var lottieAnimationView : LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        progressBar = findViewById(R.id.progressBar)
        val host = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie)
        val navController = host.findNavController()
        back = findViewById<ImageView>(R.id.back)
        var name = ""
        findViewById<ImageButton>(R.id.next).setOnClickListener {
//            progress+=0.5f
            lottieAnimationView.playAnimation()
            when (navController.currentDestination?.label) {
                "fragment_login" ->{
                    val fragment = host.childFragmentManager.fragments[0] as LoginFragment
                    val phone = "+${fragment.countryCode}${fragment.phoneBox.text}"
                    login(phone)
                }
                "fragment_name_setup" -> {
                    val fragment = host.childFragmentManager.fragments[0] as NameSetupFragment
                    name = "${fragment.firstnamebox.text} ${fragment.lastnamebox.text}"
                    navController.navigate(R.id.action_passwordFragement_to_signup)
                }
                "fragment_signup" -> {
                    val fragment = host.childFragmentManager.fragments[0] as Signup
                    val phone = "+${fragment.countryCode}${fragment.phoneBox.text}"
                    signup(name,phone)
                }
            }
        }

        back.setOnClickListener {
            lottieAnimationView.playAnimation()
           if (!navController.popBackStack())
               back.visibility = View.GONE
        }
    }

    private fun signup(name: String, phone: String) {
        if (phone.isEmpty()) {
            Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show()
        }
        if (phone.isNotEmpty()) {
            back.visibility = View.GONE
            val view =
                LayoutInflater.from(this).inflate(R.layout.bottomsheets_layout,null)
            val bottomSheetDialog =
                BottomSheetDialog(this, R.style.CustomBottomSheetDialog)
            bottomSheetDialog.setContentView(view)

            authenticationViewModel.sendOTP(
                body = SendOTPRequestBody(phone = phone)
            ).enqueue(object : Callback<String>{
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful)
                    Log.d("tag",response.body().toString())
                    else
                        Log.d("tag",response.message())

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("tag",t.message.toString())
                }

            })

            view.findViewById<ImageButton>(R.id.next).setOnClickListener {

                val otp = view.findViewById<OtpEditText>(R.id.otp).text.toString()
                if (otp.length < 6) {
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                } else {

                    authenticationViewModel.signupverifyOTP(
                        body = SignupVerifyOTPRequestBody(
                            phone = phone,
                            code = otp,
                            name = name
                        )
                    ).enqueue(
                        object : Callback<SignupOTPResponse>{
                            override fun onResponse(
                                call: Call<SignupOTPResponse>,
                                response: Response<SignupOTPResponse>
                            ) {
                                if (response.isSuccessful){
                                    progressBar.visibility = View.VISIBLE
                                    authenticationViewModel.updateOrPutToken(response.body()?.token)
                                    startActivity(Intent(this@MainActivity,ComposeActivity::class.java))
                                    finish()
                                    bottomSheetDialog.hide()
                                    lottieAnimationView.playAnimation()
                                    back.visibility = View.VISIBLE
                                }else{
                                    Toast.makeText(this@MainActivity,response.body()?.message,Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<SignupOTPResponse>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        }
                    )
                }
            }
            bottomSheetDialog.show()
        }
    }

    private fun login(phone: String) {
        if (phone.isEmpty()) {
            Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show()
        }
        if (phone.isNotEmpty()) {
            back.visibility = View.GONE
            val view =
                LayoutInflater.from(this).inflate(R.layout.bottomsheets_layout,null)
            val bottomSheetDialog =
                BottomSheetDialog(this, R.style.CustomBottomSheetDialog)
            bottomSheetDialog.setContentView(view)
            view.findViewById<ImageButton>(R.id.next).setOnClickListener {
                val otp = view.findViewById<OtpEditText>(R.id.otp).text.toString()
                if (otp.length < 6) {
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                } else {
                    progressBar.visibility = View.VISIBLE

                    authenticationViewModel.loignverifyOTP(
                        body = LoginVerifyOTPRequestBody(
                            phone,
                            code = otp
                        )
                    ).enqueue(object : Callback<LoginOTPResponse>{
                        override fun onResponse(
                            call: Call<LoginOTPResponse>,
                            response: Response<LoginOTPResponse>
                        ) {
                            Log.d("tag","${otp},$phone")
                            if (response.isSuccessful) {
                                authenticationViewModel.updateOrPutToken(response.body()?.token)
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        ComposeActivity::class.java
                                    )
                                )
                                finish()
                                bottomSheetDialog.hide()
                                lottieAnimationView.playAnimation()
                                back.visibility = View.VISIBLE
                            }else{
                                progressBar.visibility = View.INVISIBLE

                                Toast.makeText(this@MainActivity,response.body()?.message,Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(
                            call: Call<LoginOTPResponse>,
                            t: Throwable
                        ) {
                            TODO("Not yet implemented")
                        }

                    })

                }
            }
            bottomSheetDialog.show()

            authenticationViewModel.sendOTP(
                body = SendOTPRequestBody(phone = phone)
            ).enqueue(object : Callback<String>{
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.d("tag",response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("tag",t.toString())

                }

            })
        }
    }

}