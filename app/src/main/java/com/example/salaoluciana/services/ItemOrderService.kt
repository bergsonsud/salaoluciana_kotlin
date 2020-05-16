package com.example.salaoluciana.services

import com.example.salaoluciana.models.ItemOrder
import com.example.salaoluciana.models.Order
import com.google.firebase.firestore.FirebaseFirestore

class ItemOrderService private constructor(){
    companion object {
        val instace: ItemOrderService by lazy { ItemOrderService() }
    }

    val db = FirebaseFirestore.getInstance()


    fun add(order: Order,items_order: ArrayList<ItemOrder>, completion: (success: Boolean) -> Unit) {

        for (io in items_order) {
            db.collection("orders").document(order.id).collection("items").add(io).addOnSuccessListener {
                completion(true)
            }.addOnFailureListener {
                completion(false)
            }
        }

    }


    suspend fun addItems(orderId: String, items_order: ArrayList<ItemOrder>, completion: (success: Boolean) -> Unit){
        for (io in items_order){
            io.order_id = orderId
            db.collection("items_orders").add(io).addOnSuccessListener {
                completion(true)
            }.addOnFailureListener{
                completion(false)
            }
        }
    }
}