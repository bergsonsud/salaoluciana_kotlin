package com.example.salaoluciana.services

import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.util.capitalizeWords
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

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


    suspend fun searchByName(s: String) : QuerySnapshot{
        return db.collection("products")
            .orderBy("name").startAt(s.toString().capitalizeWords())
            .endAt(s.toString().capitalizeWords() + "\uf8ff").get().await()
    }


    suspend fun getProducts(s: CharSequence?) : List<Product>{
        return searchByName(s.toString()).let{
                it.map {
                    var product = it.toObject(Product::class.java)
                    product.id = it.id
                    product
                }
        }
    }


    suspend fun getProductsName(s : CharSequence?) : List<String> {
        return searchByName(s.toString()).let {
            it.map {
                it.toObject(Product::class.java).name
            }
        }
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