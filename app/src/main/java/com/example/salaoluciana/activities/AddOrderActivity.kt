package com.example.salaoluciana.activities

import BottomSheetAddProduct
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salaoluciana.R
import com.example.salaoluciana.adapters.ItemsOrderAdapter
import com.example.salaoluciana.fragments.BottomSheetAddCustomer
import com.example.salaoluciana.interfaces.BottomSheetInterface
import com.example.salaoluciana.models.Customer
import com.example.salaoluciana.models.ItemOrder
import com.example.salaoluciana.models.Order
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.services.CustomerService
import com.example.salaoluciana.services.ItemOrderService
import com.example.salaoluciana.services.OrderService
import com.example.salaoluciana.services.ProductService
import com.example.salaoluciana.util.formataParaBr
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.add_order_activity.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext


class AddOrderActivity : AppCompatActivity(), BottomSheetInterface, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    var products_order : ArrayList<Product> = arrayListOf()
    var items_order : ArrayList<ItemOrder> = arrayListOf()
    lateinit var items_order_adapter : ItemsOrderAdapter
    lateinit var bottomSheetAddProduct: BottomSheetAddProduct
    lateinit var bottomSheetAddCustomer: BottomSheetAddCustomer
    var selectedCustomerId : String? = null
    lateinit var selectedCustomer : Customer
    lateinit var orderId: String
    var orderid : Int? = 0
    var customers: List<String> = arrayListOf()
    var products: MutableList<String> = arrayListOf()
    lateinit var products_list : List<Product>
    lateinit var customers_list: List<Customer>
    var orders_list : ArrayList<Order> = arrayListOf()
    var total_value: Float = 0F
    var total_products : Int = 0
    var total_items : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_order_activity)

        products = getProds()
        setupItemsOrderAdapter()
        setupAddButton()
        fillSpinnerProductQuantity()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.salao_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                saveOrder()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun AddProduct(product: Product, quantity: Int) {
        products_order.add(product)
        items_order.add(ItemOrder("",product.id!!,product.value,quantity))
        setupItemsOrderAdapter()
        sumProducts()
    }

    override fun SetCustomer(customer: Customer) {
        selectedCustomer = customer
        selectedCustomerId=customer.id
        customer_name.text = customer.name
        icon_customer.visibility = View.VISIBLE
        bottomSheetAddCustomer.dismiss()

        startSetProducts()
    }


    override fun NewProduct() {
        bottomSheetAddProduct.dismiss()
        startSetProducts()
    }

    private fun saveOrder() {
        var order : Order = Order(selectedCustomer.id,total_value)



        launch {
            withContext(IO){
                OrderService.instance.addOrder(order){ success: Boolean, order, doc_ref ->
                    if (success) {
                        //orders_list = list
                        orderId = doc_ref.id
                        finish()
                    }
                }
            }

            withContext(IO){
               ItemOrderService.instace.addItems(orderId,items_order) {success ->
                   if (success) {
                       finish()
                   }
               }
            }


        }

    }


    private fun fillSpinnerProductQuantity() {

        var nums : ArrayList<String> = arrayListOf()

        for (x in 1..10) {
            nums.add(x.toString())
        }

        val productsQuantityAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            nums
        )

        productsQuantityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

    }

    private suspend fun startSetCustomer() {
        withContext(Dispatchers.IO) {
            customers = CustomerService.instance.getCustomersName(null)
        }
        bottomSheetAddCustomer = BottomSheetAddCustomer(this@AddOrderActivity, customers)
        bottomSheetAddCustomer.show(supportFragmentManager, "BottomSheetAddCustomer")

    }

    private fun startSetProducts() {
        bottomSheetAddProduct = BottomSheetAddProduct(this,products_list,products)
        bottomSheetAddProduct.show(supportFragmentManager, "BottomSheet")
    }


    private fun setupAddButton() {
        btn_add_product.setOnClickListener {
            startSetProducts()
        }

        btn_set_customer.setOnClickListener {
            GlobalScope.launch { startSetCustomer() }
        }
    }



    private fun sumProducts() {
        total_products = products_order.map { it.id }.count()
        total_items = items_order.map { it.quantity }.sum()
        total_value = items_order.map { it.quantity*it.product_value }.sum()
        order_total_value.text = total_value.formataParaBr()
    }


    private fun getProds() : MutableList<String>{
        var produts : MutableList<String> = ArrayList()

        ProductService.instance.getDataSnapshotListener { success, prods ->
            if (success) {
                products_list = prods
                prods.map {
                    produts.add(it.name)
                }

            }

        }

        return produts
    }

    private fun setupItemsOrderAdapter() {
        items_order_adapter = ItemsOrderAdapter(this,items_order as List<ItemOrder>,products_order as List<Product>)
        products_order_recycle_view.layoutManager = LinearLayoutManager(this)
        products_order_recycle_view.adapter = items_order_adapter
    }



}