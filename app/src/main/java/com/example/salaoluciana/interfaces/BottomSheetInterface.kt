package com.example.salaoluciana.interfaces

import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.models.Product

interface BottomSheetInterface {
    fun AddProduct(product: Product, quantity: Int)

    fun SetCustomer(customer: Customer)

    fun NewProduct()
}