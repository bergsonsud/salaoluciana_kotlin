package com.example.salaoluciana.models

import java.io.Serializable

class ItemOrder : Serializable {
    var order_id: String? = null
    var product_id: String
    var product_value: Float
    var quantity: Int

    constructor(order_id: String,product_id: String, product_value: Float, quantity: Int) {
        this.order_id = order_id
        this.product_id = product_id
        this.product_value = product_value
        this.quantity = quantity
    }

    constructor() {
        order_id = ""
        product_id = ""
        product_value = 0f
        quantity = 0
    }
}