package com.example.crimereporting.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.crimereporting.R
import com.example.crimereporting.constants.coutryCodes
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var phoneBox : TextInputEditText
    lateinit var spinner: Spinner
    lateinit var countryCode : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        phoneBox = view.findViewById(R.id.phonebox)
        view.findViewById<TextView>(R.id.createAccount).setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_loginFragment_to_passwordFragement)
        }
        countryCode = "91"
         spinner = view.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,
            coutryCodes
        ).also {
                arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
//        spinner.
        spinner.setSelection(coutryCodes.size-1)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(requireContext(),p0?.getItemAtPosition(p2).toString(), Toast.LENGTH_SHORT).show()
                countryCode = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

        }
        return view
    }
}