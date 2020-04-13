package com.example.salaoluciana.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.salaoluciana.R
import com.example.salaoluciana.interfaces.BottomSheetInterface
import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.services.CustomerService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_customer_content.*

class BottomSheetAddCustomer(var bottomSheetInterface: BottomSheetInterface, var customers : List<String>) : BottomSheetDialogFragment() {
    //var customers: MutableList<String> = arrayListOf()
    lateinit var customers_list: List<Customer>
    var selectedCustomerId : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_customer,container,false)

    }

    override fun onStart() {
        super.onStart()
        fillSpinnerCustomers()
        setupSetButton()
    }

    private fun setupSetButton() {
        set_customer_button.setOnClickListener {
            bottomSheetInterface.SetCustomer(selectedCustomerId!!)
        }
    }


    private fun fillSpinnerCustomers() {
        val customersAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!.applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            customers
        )


        customersAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        customer_spinner.adapter = customersAdapter
        customer_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               selectedCustomerId = position
            }
        }
        )

    }


}