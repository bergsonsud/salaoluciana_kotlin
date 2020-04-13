
package com.example.salaoluciana.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salaoluciana.R
import com.example.salaoluciana.adapters.CustomerAdapter
import com.example.salaoluciana.customerParam
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.services.CustomerService
import kotlinx.android.synthetic.main.customer_list_activity.*

class CustomerListActivity : AppCompatActivity(), SalaoAdapterInterface {

    lateinit var adapter: CustomerAdapter
    lateinit var dialog: Dialog




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_list_activity)

        // Access a Cloud Firestore instance from your Activity


        setupAdapter()
        fillAdapter()
        setupFloatingButton()

    }

    fun setupAdapter(){
        adapter = CustomerAdapter(this,this, arrayListOf())
        recycletview.layoutManager = LinearLayoutManager(this)
        recycletview.adapter = adapter
    }

    fun fillAdapter(){
        CustomerService.instance.getData { success, customers ->
            if (success) {
                adapter.items = customers
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun setupFloatingButton(){
        addCustomer.setOnClickListener {
            startActivity(Intent(this, AddCustomerActivity::class.java))
        }
    }


    private fun makeChoice(which: Int, position: Int) {
        dialog.dismiss()
        when (which) {
            0 -> showEditCustomerActivity(position)
            else -> {
                showDeleteDialog(position)
            }
        }
    }

    override fun onItemClick(position: Int) {

        showEditCustomerActivity(position)
    }

    override fun onItemLongClick(position: Int) {
       //showDeleteDialog(position)
        showOptionsDialog(position)
    }


    private fun showOptionsDialog(position: Int) {

        val options = arrayOf("Editar","Excluir")
        val dialogBuilder = AlertDialog.Builder(this)


        dialogBuilder.setTitle("Selecione acao")
        dialogBuilder.setSingleChoiceItems(options,-1,{_,which->
            makeChoice(which, position)

        })

        dialog = dialogBuilder.create()
        dialog.show()
    }


    private fun showEditCustomerActivity(position: Int) {
        var intent = Intent(this,AddCustomerActivity::class.java)
        intent.putExtra(customerParam,adapter.items[position])
        startActivity(intent)
    }

    private fun showDeleteDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setTitle("Deletar cliente")
        dialogBuilder.setMessage("Voce deseja cancelar?")

        dialogBuilder.setPositiveButton("Sim"){_,_ ->
            deleteCustomer(position)
        }


        val dialog: Dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun deleteCustomer(position: Int) {
        CustomerService.instance.deleteCustomer(adapter.items[position])
    }

  
}

