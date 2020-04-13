package com.example.salaoluciana.interfaces

import com.example.salaoluciana.models.Product

interface BottomSheetInterface {
    fun AddProduct(product: Product, quantity: Int)

    fun SetCustomer(customer_id: Int)
}