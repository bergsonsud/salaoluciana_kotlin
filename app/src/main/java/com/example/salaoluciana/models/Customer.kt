package com.example.salaoluciana.models

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Customer: Serializable {
    @Exclude
    var id: String? = null
    var user_id: String
    var name: String
    var phone: String


    constructor(user_id: String,name: String, phone:String) {
        this.user_id = user_id
        this.name = name
        this.phone = phone
    }

    constructor() {
        user_id = ""
        name = ""
        phone = ""
    }
}