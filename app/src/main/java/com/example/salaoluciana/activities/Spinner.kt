package com.example.salaoluciana.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.salaoluciana.R
import kotlinx.android.synthetic.main.spinner_test.*


class Spinner : AppCompatActivity() {

    private var mContext: Context? = null
    private var mActivity: Activity? = null

    private var mCLayout: CoordinatorLayout? = null
    private var mTextView: TextView? = null
    private val mAdapter: ArrayAdapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spinner_test)


        mContext = applicationContext
        mActivity = this

        // Get the widget reference from XML layout
        // Get the widget reference from XML layout
        mCLayout = findViewById(R.id.coordinator_layout) as CoordinatorLayout
        mTextView = findViewById(R.id.text_view) as TextView


        // Initialize a new list
        // Initialize a new list
        val flowers: MutableList<String> = ArrayList()
        flowers.add("Aconitum")
        flowers.add("African Daisy")
        flowers.add("Alchemilla")
        flowers.add("Allium roseum")
        flowers.add("Alstroemeria")

        // Initialize an array adapter
        // Initialize an array adapter
        var mAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            flowers
        )
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        // Data bind the spinner with array adapter items
        // Data bind the spinner with array adapter items
        spinner.setAdapter(mAdapter)


        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) { // Get the spinner selected item text
                val selectedItemText =
                    adapterView.getItemAtPosition(i) as String
                // Display the selected item into the TextView
                mTextView!!.text = "Selected : $selectedItemText"
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                Toast.makeText(mContext, "No selection", Toast.LENGTH_LONG).show()
            }
        })

    }
}