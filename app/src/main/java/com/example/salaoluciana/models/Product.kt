package com.example.salaoluciana.models

import java.io.Serializable

class Product : Serializable {
    //@Exclude
    var id: String? = null
    var name: String
    var value: Float


    constructor(name: String, value: Float){
        this.name = name
        this.value = value
    }

    constructor(){
        this.name = ""
        this.value = 0f

    }
}