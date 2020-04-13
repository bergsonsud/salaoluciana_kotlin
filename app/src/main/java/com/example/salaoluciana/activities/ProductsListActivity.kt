package com.example.salaoluciana.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salaoluciana.R
import com.example.salaoluciana.adapters.ProductAdapter
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.services.ProductService
import kotlinx.android.synthetic.main.product_list_activity.*

class ProductsListActivity : AppCompatActivity(),SalaoAdapterInterface {

    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_list_activity)


        setupFloatingButton()
        setupAdapter()
        fillAdapter()
        fillTotal()





    }

    fun setupAdapter(){
        adapter = ProductAdapter(this,this, arrayListOf())
        recycleview_product.layoutManager = LinearLayoutManager(this)
        recycleview_product.adapter = adapter
    }

    fun fillAdapter(){
        ProductService.instance.getDataSnapshotListener{
                sucess, products ->
            if (sucess){
                adapter.products = products
                adapter.notifyDataSetChanged()
            }
        }
    }


    fun fillTotal(){
        ProductService.instance.getDataSnapshotListener { sucess, products ->
            if (sucess){

                //total_value.text = products.map { it.value.toString().toInt() }.sum().toString()

            }
        }
    }

    fun setupFloatingButton(){


//
//        var p : CoordinatorLayout.LayoutParams = addProduct.layoutParams as CoordinatorLayout.LayoutParams
//        p.behavior = ScrollAwareFABBehavior(this, null)
//        addProduct.layoutParams = p


        addProduct.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
    }

    override fun onItemClick(position: Int) {
        Log.i("Product", position.toString())
    }

    override fun onItemLongClick(position: Int) {
        Log.i("Product", position.toString())
    }
}