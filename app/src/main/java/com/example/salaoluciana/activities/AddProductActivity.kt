package com.example.salaoluciana.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.salaoluciana.R
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.services.ProductService
import kotlinx.android.synthetic.main.add_product_activity.*
import kotlinx.android.synthetic.main.item_order_row.view.*

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product_activity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.salao_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.save -> {
                saveProduct()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun saveProduct(){
        val service = ProductService.instance

        val name: String = EditText_name.text.toString()
        val value: Float = EditText_value.text.toString().toFloat()
        val product = Product(name, value)

        service.createProduct(product){sucess ->
            if (sucess){
                finish()
            }
        }

    }
}