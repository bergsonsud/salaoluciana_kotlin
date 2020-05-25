package com.example.salaoluciana.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salaoluciana.R
import com.example.salaoluciana.adapters.OrderAdapter
import com.example.salaoluciana.adapters.ProductAdapter
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.services.OrderService
import com.example.salaoluciana.services.ProductService
import kotlinx.android.synthetic.main.order_list_activity.*
import kotlinx.android.synthetic.main.product_list_activity.*

class OrderListFragment : Fragment(), SalaoAdapterInterface {
    lateinit var adapter : OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_list_activity, container,false)
    }


    override fun onStart() {
        super.onStart()
        setupAdapter()
        fillAdapter()
    }

    override fun onItemClick(position: Int) {

    }

    override fun onItemLongClick(position: Int) {

    }

    private fun setupAdapter() {

        adapter = OrderAdapter(this,context!!, arrayListOf())
        recycleview_order.layoutManager = LinearLayoutManager(context!!)
        recycleview_order.adapter = adapter

    }

    private fun fillAdapter() {

        OrderService.instance.getDataSnapshotListener{ success, orders ->
            if (success){
                Log.i("asd",orders.count().toString())
                adapter.orders = orders
                adapter.notifyDataSetChanged()
            }
        }


    }

}