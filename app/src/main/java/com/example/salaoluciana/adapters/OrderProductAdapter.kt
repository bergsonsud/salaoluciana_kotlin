package com.example.salaoluciana.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salaoluciana.R
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.util.formataParaBr
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_row.*

class OrderProductAdapter(var context : Context, var products: List<Product>) : RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_order_row,parent, false)
        return OrderProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        holder.name.text = products[position].name
        holder.value.text = products[position].value.formataParaBr()
    }

    inner class OrderProductViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer{


    }
}