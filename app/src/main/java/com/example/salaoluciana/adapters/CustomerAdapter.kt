package com.example.salaoluciana.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.R
import com.example.salaoluciana.interfaces.SalaoAdapterInterface
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.customer_row.*

class CustomerAdapter(
    var salaoAdapterInterface: SalaoAdapterInterface,
    var context: Context,
    var items: List<Customer>) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.customer_row, parent, false)
        return CustomerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }


    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = items[position]

        holder.nome.text = item.name
        holder.phone.text = item.phone

        holder.containerView.setOnClickListener(holder)
        holder.containerView.setOnLongClickListener(holder)

    }

    inner class CustomerViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            salaoAdapterInterface.onItemClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            salaoAdapterInterface.onItemLongClick(adapterPosition)
            return true
        }
    }
}