package com.example.salaoluciana.services

import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.models.Order
import com.google.firebase.firestore.FirebaseFirestore

class OrderService private constructor(){
    companion object {
        val instance: OrderService by lazy { OrderService() }
    }

    val db = FirebaseFirestore.getInstance()


    fun addOrder(order: Order, completion: (sucess: Boolean, ArrayList<Order>) -> Unit) {
        db.collection("orders").add(order).addOnSuccessListener {
            completion(true, it.get().result!!.toObject(Order::class.java)!!.let {
                var orders : ArrayList<Order> = arrayListOf()
                var order = it
                order.id = it.id
                orders.add(order)
                orders
            })
        }.addOnFailureListener {
            completion(false, arrayListOf<Order>())
        }
    }
}