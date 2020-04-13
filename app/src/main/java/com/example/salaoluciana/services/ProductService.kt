package com.example.salaoluciana.services

import com.example.salaoluciana.models.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ProductService private constructor(){

    companion object {
        val instance: ProductService by lazy { ProductService() }
    }

    val db = FirebaseFirestore.getInstance()



    fun getUId() : String? {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun getData2(completion: ((Boolean, List<Product>)-> Unit)) {
        db.collection("Products").get().addOnSuccessListener { result ->
            val products = result.toObjects(Product::class.java)
             completion(true, products)
         }
    }

    fun getData(param: (Any, Any) -> Unit): Task<QuerySnapshot> {
        return db.collection("Products").get()
    }

    fun getDataSnapshotListener(completion: ((Boolean, List<Product>)-> Unit)){
        db.collection("products")
            .addSnapshotListener { querySnapshot, error ->
                querySnapshot.let {
                    val products = querySnapshot!!.map {
                        var product = it.toObject(Product::class.java)
                        product.id = it.id
                        product
                    }

                    completion(true, products)


                }

                error.let {
                    completion(false, arrayListOf())
                }
            }
    }


    fun createProduct(product: Product, completion: (sucess: Boolean) -> Unit){
        db.collection("products").add(product).addOnSuccessListener {
            completion (true)
        }.addOnFailureListener {
            completion (false)
        }
    }

    fun deleteProduct(product: Product){
        db.collection("products").document(product.id!!).delete()
    }


}