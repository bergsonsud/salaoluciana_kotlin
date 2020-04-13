package com.example.salaoluciana.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.salaoluciana.R
import com.example.salaoluciana.services.CustomerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TestActivity : AppCompatActivity() {

    var customers : List<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        GlobalScope.launch {
          getData()
        }
        setupAutocomplete2()
    }

    private fun setupAutocomplete2() {
        var languages  = resources.getStringArray(R.array.languages)
        val planets =
            arrayOf("Mercury", "Venus", "Earth", "Mars")

        var names : MutableList<String> = arrayListOf()

        names.add("Bergson")
        names.add("Bruno")




        var auto = findViewById<AutoCompleteTextView>(R.id.autocomplete)
        var arrayAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, names as List<String>)
        auto.setAdapter(arrayAdapter)
    }

    private suspend fun getData() {
        withContext(Dispatchers.IO) {
            customers = CustomerService.instance.getCustomersName()
        }


    }
}