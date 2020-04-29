package com.example.salaoluciana.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.google.api.CustomHttpPatternOrBuilder
import kotlinx.android.synthetic.main.bottom_sheet_add_customer_content.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine


class BottomSheetAddCustomer(var bottomSheetInterface: BottomSheetInterface, var customers : List<String>, var customer_list : List<Customer>) : BottomSheetDialogFragment(), CoroutineScope {
    var selectedCustomerId : Int? = null
    lateinit var data : List<String>

    override val coroutineContext: CoroutineContext = Dispatchers.Main

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
       // setupAutocomplete()
        setupautocompletedb()


    }



    private fun setupArray(data : List<String>) : ArrayAdapter<String>{
        return ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, data)
    }

    private fun setupAutocomplete() {

        autocompletetextview.setAdapter(setupArray(customers))

        autocompletetextview.setOnItemClickListener { parent, view, position, id ->
            val vo = parent.getItemAtPosition(position)
            Log.i("assd",vo.toString())
        }

    }


    private fun setupautocompletedb() {

        var adapter = setupArray(customers)
        autocompletetextview.setAdapter(adapter)

        autocompletetextview.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                launch {
                    withContext(IO) {
                        customers = CustomerService.instance.getCustomersName(s)
                    }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
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