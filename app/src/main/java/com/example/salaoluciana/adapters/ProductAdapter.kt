package com.example.salaoluciana.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salaoluciana.R
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import com.example.salaoluciana.models.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_row.*

class ProductAdapter(var salaoAdapterInterface: SalaoAdapterInterface,var context: Context, var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.product_row,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.name.text = products[position].name
        holder.value.text = products[position].value.toString()

        holder.containerView.setOnClickListener(holder)
        holder.containerView.setOnLongClickListener(holder)
    }

    inner class ProductViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView)
        ,LayoutContainer
        ,View.OnClickListener
        ,View.OnLongClickListener {
        override fun onClick(v: View?) {
            salaoAdapterInterface.onItemClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            salaoAdapterInterface.onItemLongClick(adapterPosition)
            return true
        }
    }
}