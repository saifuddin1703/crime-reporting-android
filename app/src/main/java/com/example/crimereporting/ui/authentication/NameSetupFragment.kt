package com.example.crimereporting.ui.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.crimereporting.R
import com.google.android.material.textfield.TextInputEditText


class NameSetupFragment : Fragment() {

    val PASSWORD_SCREEN = 1
    val NAME_SCREEN = 2

      var screenVisible = NAME_SCREEN
      lateinit var tem2:TextView
      lateinit var tem3:TextView
      lateinit var firstnamebox:TextInputEditText
      lateinit var lastnamebox:TextInputEditText
     var name:String= ""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("tag","password attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag","password create")

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_setup, container, false)
        tem2 = view.findViewById(R.id.template2)
//        tem3 = view.findViewById(R.id.template3)
        firstnamebox = view.findViewById(R.id.firstname)
        lastnamebox = view.findViewById(R.id.lastname)
//        password = view.findViewById(R.id.password)
//        confirmpasswore = view.findViewById(R.id.confirm)

        return view
    }

    fun startanimation(){
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.template_animation)
        val animation2 = AnimationUtils.loadAnimation(requireContext(), R.anim.template3_animation)
        val boxanimation = AnimationUtils.loadAnimation(requireContext(), R.anim.namebox_animation)
        val fadeinanimation = AnimationUtils.loadAnimation(requireContext(),
            R.anim.fade_in_animation
        )
        name = "${firstnamebox.text} ${lastnamebox.text}"

              if (name.isNotEmpty()){
            tem2.startAnimation(animation)
            tem2.visibility = View.INVISIBLE
            tem3.visibility = View.VISIBLE
            tem3.startAnimation(animation2)
            tem3.text = name

            firstnamebox.startAnimation(boxanimation)

            firstnamebox.visibility = View.INVISIBLE

            lastnamebox.startAnimation(boxanimation)
            lastnamebox.visibility = View.INVISIBLE

        }else{
                  Toast.makeText(requireContext(),"Name fields cannot be empty",Toast.LENGTH_SHORT).show()
              }
    }
    override fun onPause() {
        super.onPause()
        Log.d("tag","password pause")

    }

    override fun onResume() {
        super.onResume()
        Log.d("tag","password resume")

    }
    override fun onStop() {
        super.onStop()
        Log.d("tag","password attach")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("tag","password deattach")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("tag","password destroy")

    }


}