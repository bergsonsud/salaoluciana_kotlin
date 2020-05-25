package com.example.salaoluciana.services

import android.util.Log
import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.models.Order
import com.example.salaoluciana.models.Product
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrderService private constructor(){
    companion object {
        val instance: OrderService by lazy { OrderService() }
    }

    val db = FirebaseFirestore.getInstance()

    fun getDataSnapshotListener(completion: ((Boolean, List<Order>)-> Unit)){
        db.collection("orders")
            .addSnapshotListener { querySnapshot, error ->
                querySnapshot.let {
                    val orders = querySnapshot!!.map {
                        var order = it.toObject(Order::class.java)
                        order.id = it.id
                        order
                    }

                    completion(true, orders)


                }

                error.let {
                    completion(false, arrayListOf())
                }
            }
    }

    fun getOrders(completion: (sucess: Boolean, List<Order>) -> Unit) {
        db.collection("orders").addSnapshotListener{ querySnapshot, exception ->
            querySnapshot.let {

                val orders = it!!.map {
                    val order = it.toObject(Order::class.java)
                    order.id = it.id
                    order
                }

                completion(true, orders)
            }

            exception.let {
                completion(false, arrayListOf())
            }

        }
    }


    suspend fun addOrder(order: Order, completion: (sucess: Boolean, order: Order?, doc_ref: DocumentReference) -> Unit) {

            var doc_ref = db.collection("orders").add(order).await()
            doc_ref.get().addOnSuccessListener {
                var o = it.toObject(Order::class.java)
                completion(true,o as Order,doc_ref)
            }.addOnFailureListener{
                completion(false,order,doc_ref)
            }.await()



    }

    fun addOrder2(order: Order, completion: (sucess: Boolean, ArrayList<Order>, doc_ref: DocumentReference?) -> Unit) {
        db.collection("orders").add(order).addOnSuccessListener {
            completion(true, it.get().result!!.toObject(Order::class.java)!!.let {
                Log.i("asd","adicionada")
                var orders : ArrayList<Order> = arrayListOf()
                var order = it
                order.id = it.id
                orders.add(order)
                orders
            },it)
        }.addOnFailureListener {
            completion(false, arrayListOf<Order>(),null)
        }
    }
}