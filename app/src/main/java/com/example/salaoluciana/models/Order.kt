package com.example.salaoluciana.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Order : Serializable {
    @get:Exclude
    var id: String = ""
    var customer_id: String
    var customer_name: String
    var value: Float



    constructor(customer_id: String, customer_name: String,value: Float) {
        this.customer_id = customer_id
        this.customer_name = customer_name
        this.value = value

    }

    constructor(){
        customer_id = ""
        customer_name = ""
        value = 0F
    }
}