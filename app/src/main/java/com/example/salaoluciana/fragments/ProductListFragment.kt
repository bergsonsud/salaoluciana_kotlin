package com.example.salaoluciana.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salaoluciana.R
import com.example.salaoluciana.activities.AddProductActivity
import com.example.salaoluciana.adapters.ProductAdapter
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.services.ProductService
import kotlinx.android.synthetic.main.product_list_activity.*

class ProductListFragment : Fragment(), SalaoAdapterInterface {

    lateinit var adapter : ProductAdapter
    lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list_activity,container,false)
    }


    override fun onStart() {
        super.onStart()
        setupAdapter()
        fillAdapter()
        setupFloatingButton()
    }

    private fun fillAdapter() {

        ProductService.instance.getDataSnapshotListener{ success, products ->
            if (success){
                adapter.products = products
                adapter.notifyDataSetChanged()
            }
        }


    }

    private fun setupAdapter() {

        adapter = ProductAdapter(this,context!!, arrayListOf())
        recycleview_product.layoutManager = LinearLayoutManager(context!!)
        recycleview_product.adapter = adapter

    }

    private fun setupFloatingButton(){
        addProduct.setOnClickListener{
            startActivity(Intent(context,AddProductActivity::class.java))
        }
    }

    private fun showOptionDialog(position: Int) {
        var options = arrayOf("Editar","Excluir")
        var dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle("Produto")

        dialogBuilder.setSingleChoiceItems(options,-1,{_, which ->
            makeChoice(which,position)
        })


        dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun makeChoice(which: Int,position: Int) {
        dialog.dismiss()

        when (which){
            0 -> showEditProductActivity(position)
            else -> {
                showDeleteProductDialog(position)
            }
        }
    }

    private fun showEditProductActivity(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showDeleteProductDialog(position: Int) {

        var dialog : Dialog
        var dialogBuider = AlertDialog.Builder(context!!)

        dialogBuider.setTitle("Produto")
        dialogBuider.setMessage("Deletar Produto?")


        dialogBuider.setPositiveButton("Sim") {_,_ ->
            deleteProduct(adapter.products[position])
        }


        dialog = dialogBuider.create()
        dialog.show()



    }

    private fun deleteProduct(product: Product) {
        ProductService.instance.deleteProduct(product)
    }

    override fun onItemClick(position: Int) {
        //Log.i("Product", position.toString())
    }

    override fun onItemLongClick(position: Int) {
        Log.i("Product", position.toString())
        showOptionDialog(position)
    }
}