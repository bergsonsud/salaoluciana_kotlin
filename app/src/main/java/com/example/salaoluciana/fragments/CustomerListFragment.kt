package com.example.salaoluciana.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salaoluciana.R
import com.example.salaoluciana.activities.AddCustomerActivity
import com.example.salaoluciana.adapters.CustomerAdapter
import com.example.salaoluciana.customerParam
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.services.CustomerService
import kotlinx.android.synthetic.main.customer_list_activity.*

class CustomerListFragment : Fragment(), SalaoAdapterInterface{


    lateinit var adapter: CustomerAdapter
    lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.customer_list_activity,container,false)
    }

    override fun onStart() {
        super.onStart()
        setupAdapter()
        fillAdapter()
        setupFloatingButton()
        setupSortButton()
    }



    private fun setupAdapter() {
        adapter = CustomerAdapter(this, context!!, arrayListOf())
        recycletview.layoutManager = LinearLayoutManager(context!!)
        recycletview.adapter = adapter
    }

    private fun fillAdapter(){
        CustomerService.instance.getData { success, customers ->
            if (success) {
                adapter.items = customers
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupFloatingButton(){
        addCustomer.setOnClickListener {
            startActivity(Intent(context, AddCustomerActivity::class.java))
        }
    }

    private fun setupSortButton(){
        sortCustomer.setOnClickListener {
            CustomerService.instance.getDataSorted{success,customers ->
                if (success){
                    adapter.items = customers
                    adapter.notifyDataSetChanged()
                }
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
        val dialogBuilder = AlertDialog.Builder(context!!)


        dialogBuilder.setTitle("Selecione acao")
        dialogBuilder.setSingleChoiceItems(options,-1,{_,which->
            makeChoice(which, position)

        })

        dialog = dialogBuilder.create()
        dialog.show()
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

    private fun showEditCustomerActivity(position: Int) {
        var intent = Intent(context,AddCustomerActivity::class.java)
        intent.putExtra(customerParam,adapter.items[position])
        startActivity(intent)
    }

    private fun showDeleteDialog(position: Int) {
        val dialogBuilder = AlertDialog.Builder(context!!)

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