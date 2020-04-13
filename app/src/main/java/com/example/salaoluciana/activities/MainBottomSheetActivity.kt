package com.example.salaoluciana.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.salaoluciana.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main_bottomsheet.*

class MainBottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_bottomsheet)

        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setHideable(false)
    }
}