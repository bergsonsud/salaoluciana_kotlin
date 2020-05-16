package com.example.salaoluciana.services


import android.util.Log
import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.util.capitalizeWords
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.plus
import kotlinx.coroutines.tasks.await

class CustomerService private constructor() {
    lateinit var querySnapshotNames : QuerySnapshot

    companion object {
        val instance: CustomerService by lazy { CustomerService() }
    }

    val db = FirebaseFirestore.getInstance()


    fun getUId () : String? {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun getDataSorted(completion: (Boolean, List<Customer>) -> Unit){
        db.collection("customers").whereEqualTo("user_id", getUId())
            .orderBy("name").addSnapshotListener{
            querySnapshot, error ->

            querySnapshot?.let {
                val customers = querySnapshot.map {
                    val customer = it.toObject(Customer::class.java)
                    customer.id = it.id
                    customer
                }
                completion(true, customers)
            }
            error.let {
                completion(false, arrayListOf())
            }

        }
    }


     suspend fun getCustomersName2(): MutableList<String> {
        var names : ArrayList<String> = arrayListOf()
        db.collection("customers").whereEqualTo("user_id",getUId())
            .addSnapshotListener{ querySnapshot, error ->
                querySnapshot?.let {
                    it.map {
                        names.add(it.toObject(Customer::class.java).name)
                        it
                    }
                }

            }
        return names
    }

    suspend fun getCustomersName(s : CharSequence?): List<String> {
        if (s.isNullOrEmpty()) {
            querySnapshotNames = db.collection("customers").
            whereEqualTo("user_id",getUId()).get().await()
        }

        else{
            querySnapshotNames = db.collection("customers").orderBy("name")
                .startAt(s.toString().capitalizeWords()).endAt(s.toString().capitalizeWords() + "\uf8ff")
                .get().await()
        }

        return querySnapshotNames?.map {
            it.toObject(Customer::class.java).name
        }
    }


    suspend fun getCustomers(s : CharSequence?): List<Customer> {
        if (s.isNullOrEmpty()) {
            querySnapshotNames = db.collection("customers").
            whereEqualTo("user_id",getUId()).get().await()
        }

        else{
            s.let {
                querySnapshotNames = db.collection("customers").orderBy("name").
                startAt(s.toString().capitalizeWords()).endAt(s.toString().capitalizeWords() + "\uf8ff").get().await()
            }
        }

        return querySnapshotNames?.map {
            val customer = it.toObject(Customer::class.java)
            customer.id = it.id
            customer
        } ?: listOf()
    }


    fun getData(completion: ((Boolean, List<Customer>) -> Unit)) {

        db.collection("customers").whereEqualTo("user_id", getUId())
            .addSnapshotListener { querySnapshot, error ->
                querySnapshot?.let {
                    //val customers = querySnapshot.toObjects(Customer::class.java)

                    //  val customers = arrayListOf<Customer>()


//                    for (document in querySnapshot) {
//                        val customer = document.toObject(Customer::class.java)
//                        customer.id = document.id
//                        customers.add(customer)
//                    }

                    val customers = querySnapshot.map {
                        val customer = it.toObject(Customer::class.java)
                        customer.id = it.id

                        customer
                    }


                    completion(true, customers)
                }

                error?.let {
                    completion(false, arrayListOf())
                }
            }
    }


    fun addCustomer(customer: Customer,completion: (sucess: Boolean) -> Unit) {
        db.collection("customers").add(customer).addOnSuccessListener {
            completion(true)
        }.addOnFailureListener {
            completion(false)
        }
    }

    fun updateCustomer(customer: Customer, completion: (sucess: Boolean) -> Unit) {
        val userRef = db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
        userRef.get().addOnCompleteListener {

        }

        db.collection("customers").document(customer.id!!)
            .set(customer)
            .addOnSuccessListener {
                completion(true)
            }
            .addOnFailureListener {
               completion( false)
            }
    }


    fun deleteCustomer(customer: Customer) {
        db.collection("customers").document(customer.id!!)
            .delete()
    }


}