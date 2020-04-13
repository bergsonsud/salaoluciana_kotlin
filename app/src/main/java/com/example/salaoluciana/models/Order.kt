package com.example.salaoluciana.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Order : Serializable {
    @get:Exclude
    var id: String = ""
    var customer_id: String
    var value: Float



    constructor(customer_id: String, value: Float) {
        this.customer_id = customer_id
        this.value = value

    }

    constructor(){
        customer_id = ""
        value = 0F
    }
}