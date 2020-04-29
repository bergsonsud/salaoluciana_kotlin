package com.example.salaoluciana.activities

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.salaoluciana.R
import com.example.salaoluciana.services.CustomerService
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TestActivity : AppCompatActivity() {

    var customers : List<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        test_activity.setOnClickListener {
            Log.i("asd","tela tocada")
        }


        GlobalScope.launch {
          getData()
        }
        setupAutocomplete2()
    }


    private fun setupAutocomplete2() {
//        var languages  = resources.getStringArray(R.array.languages)
//        val planets =
//            arrayOf("Mercury", "Venus", "Earth", "Mars")

        var names : MutableList<String> = arrayListOf()

        names.add("Bergson")
        names.add("Bruno")

        var arrayAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, names as List<String>)
        autocomplete.setAdapter(arrayAdapter)
    }

    private suspend fun getData() {
        withContext(Dispatchers.IO) {
            customers = CustomerService.instance.getCustomersName(null)
        }


    }
}