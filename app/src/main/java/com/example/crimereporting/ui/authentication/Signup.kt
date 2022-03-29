package com.example.crimereporting.ui.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.crimereporting.R
import com.example.crimereporting.constants.coutryCodes
import com.google.android.material.textfield.TextInputEditText

class Signup : Fragment() {
    lateinit var phoneBox : TextInputEditText
    lateinit var spinner: Spinner
    lateinit var countryCode : String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("tag","signup attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag","signup create")

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,
            coutryCodes).also {
                arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
//        spinner.
        spinner.setSelection(coutryCodes.size-1)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(requireContext(),p0?.getItemAtPosition(p2).toString(),Toast.LENGTH_SHORT).show()
                countryCode = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

        }
        phoneBox = view.findViewById(R.id.phonebox)
        return view
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("tag","signup resume")

    }
    override fun onStop() {
        super.onStop()

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("tag","signup deattach")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("tag","signup destroy")

    }
}