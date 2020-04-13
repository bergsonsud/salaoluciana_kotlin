package com.example.salaoluciana.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.salaoluciana.R
import com.example.salaoluciana.customerParam
import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.services.CustomerService
import kotlinx.android.synthetic.main.add_customer_activity.*

class AddCustomerActivity : AppCompatActivity() {

    var customer: Customer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_customer_activity)

        loadCustomer()
        fillEditText()

    }



    private fun fillEditText() {
       customer?.let {
           nomeEditText.setText(it.name)
           phoneEditText.setText(it.phone)
       }
    }

    private fun loadCustomer() {
        customer = intent.getSerializableExtra(customerParam) as? Customer
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.salao_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.save -> {
                saveCustomer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun createCustomer(nome: String, phone: String){

        var service : CustomerService = CustomerService.instance

        var customer = Customer(service.getUId().toString(),nome,phone)

        service.addCustomer(customer){ sucess ->
            if (sucess){
                finish()
            }
        }
    }


    private fun updateCustomer(nome: String, phone: String){
        customer?.name = nome
        customer?.phone = phone



        CustomerService.instance.updateCustomer(customer!!){sucess ->
            if (sucess){
                finish()
            }
        }
    }

    private fun saveCustomer() {


        val nome = nomeEditText.text.toString()
        val phone = phoneEditText.text.toString()



        if (customer==null){
            createCustomer(nome,phone)
        }
        else{
            updateCustomer(nome,phone)
        }





    }


}
