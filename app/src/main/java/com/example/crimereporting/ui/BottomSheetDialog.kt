package com.example.crimereporting.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import com.example.crimereporting.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialog(val context: Context) {

    lateinit var view: View
    fun showdialog(){
        view = LayoutInflater.from(context).inflate(R.layout.bottomsheets_layout,null)
        val bottomSheetDialog = BottomSheetDialog(context, R.style.CustomBottomSheetDialog)
        bottomSheetDialog.setContentView(view)

        bottomSheetDialog.show()
    }


    fun Onclicklistner(onclick:(View)->Unit){
        view.findViewById<ImageButton>(R.id.next)
            .setOnClickListener(onclick)
    }
}