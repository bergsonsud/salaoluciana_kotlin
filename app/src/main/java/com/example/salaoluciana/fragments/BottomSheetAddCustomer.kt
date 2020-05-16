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
import kotlinx.android.synthetic.main.bottom_sheet_add_customer_content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class BottomSheetAddCustomer(var bottomSheetInterface: BottomSheetInterface, var customers : List<String>) : BottomSheetDialogFragment(), CoroutineScope {
    var selectedCustomerId : Int? = null
    var selectedPosition : Int? = null
    lateinit var data : List<String>
    lateinit var customer_list : List<Customer>

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

        customers = listOf()
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
                        customer_list = CustomerService.instance.getCustomers(s)
                        customers = CustomerService.instance.getCustomersName(s)
                    }
                    withContext(Dispatchers.Main){
                        adapter.clear()
                        customers.map {
                            adapter.add(it)
                        }
                        adapter.notifyDataSetChanged()
                    }

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

        autocompletetextview.setOnItemClickListener { parent, view, position, id ->
            val vo = parent.getItemAtPosition(position)
            selectedPosition = position
        }
    }

    private fun setupSetButton() {
        set_customer_button.setOnClickListener {
            bottomSheetInterface.SetCustomer(customer_list[selectedPosition!!])
        }
    }

}