package com.example.salaoluciana.models

import java.io.Serializable

class ItemOrder : Serializable {
    var product_id: String
    var product_value: Float
    var quantity: Int

    constructor(product_id: String, product_value: Float, quantity: Int) {
        this.product_id = product_id
        this.product_value = product_value
        this.quantity = quantity
    }

    constructor() {
        product_id = ""
        product_value = 0f
        quantity = 0
    }
}