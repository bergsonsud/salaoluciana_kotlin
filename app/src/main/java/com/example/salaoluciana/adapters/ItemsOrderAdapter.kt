package com.example.salaoluciana.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salaoluciana.R
import com.example.salaoluciana.models.ItemOrder
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.util.formataParaBr
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_row.*

class ItemsOrderAdapter(var context : Context, var itemsOrder: List<ItemOrder>,
                        var products: List<Product>) : RecyclerView.Adapter<ItemsOrderAdapter.ItemsOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsOrderViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_order_row,parent, false)
        return ItemsOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsOrder.count()
    }

    override fun onBindViewHolder(holder: ItemsOrderViewHolder, position: Int) {
        var quantity = itemsOrder[position].quantity
        var value = itemsOrder[position].product_value
        var total = quantity*value


        holder.name.text = products[position].name
        holder.quantity.text = quantity.toString()
        holder.value.text = value.formataParaBr()
        holder.total.text = total.formataParaBr()

        Log.i("asd",position.toString())
    }

    inner class ItemsOrderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer{


    }
}