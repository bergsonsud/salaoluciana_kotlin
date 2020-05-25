package com.example.salaoluciana.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salaoluciana.R
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.models.Order
import com.example.salaoluciana.util.formataParaBr
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_row.*
import kotlinx.android.synthetic.main.product_row.*
import java.util.zip.Inflater

class OrderAdapter(var salaoAdapterInterface: SalaoAdapterInterface,var content : Context, var orders : List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        var view = LayoutInflater.from(content).inflate(R.layout.order_row,parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.count()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.customer_name.text = orders[position].customer_name
        holder.order_value.text = orders[position].value.formataParaBr()

        holder.containerView.setOnClickListener(holder)
        holder.containerView.setOnLongClickListener(holder)
    }


 inner class OrderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
     LayoutContainer,
     View.OnClickListener,
     View.OnLongClickListener{

     override fun onClick(v: View?) {
         salaoAdapterInterface.onItemClick(adapterPosition)
     }

     override fun onLongClick(v: View?): Boolean {
         salaoAdapterInterface.onItemLongClick(adapterPosition)
         return true
     }

 }

}