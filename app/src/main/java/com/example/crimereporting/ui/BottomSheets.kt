package com.example.crimereporting.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.crimereporting.utils.toPx
import com.example.crimereporting.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheets : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheets_layout,container,false)

        val dialog = dialog
//        val dview = dialog?.findViewById<FrameLayout>(R.id.bottomsheetdailog)
//        dview?.layoutParams?.height = 300.toPx(requireContext()).toInt()

        val layout = view.findViewById<ConstraintLayout>(R.id.bottomsheetdailog)
        layout.clipToOutline = true
        layout.maxHeight = 300.toPx(requireContext()).toInt()

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.bottomsheets_layout,null,false)
        view.layoutParams?.height = 300.toPx(requireContext()).toInt()
        dialog.setContentView(view)
        return dialog
    }
}