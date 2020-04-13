import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.salaoluciana.R
import com.example.salaoluciana.interfaces.BottomSheetInterface
import com.example.salaoluciana.models.Product
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_product_content.*

class BottomSheetAddProduct(var bottomSheetInterface: BottomSheetInterface,
                            var products_list : List<Product>,
                            var products : MutableList<String>) : BottomSheetDialogFragment() {

    private var mBottomSheetListener: BottomSheetListener?=null
    var selectedQuantity : Int? = null
    var selectedProductId : Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_product, container, false)
    }


    override fun onStart() {
        super.onStart()
        fillSpinnerProductQuantity()
        fillSpinnerProducts()
        setupAddButton()
    }



    private fun setupAddButton() {
        add_product_button.setOnClickListener {
            bottomSheetInterface.AddProduct(products_list[selectedProductId!!],selectedQuantity!!)
        }
    }

    private fun fillSpinnerProductQuantity() {

        var nums : ArrayList<String> = arrayListOf()

        for (x in 1..10) {
            nums.add(x.toString())
        }


        val productsQuantityAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
            context!!.applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            nums
        )

        productsQuantityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        products_quantity_spinner.adapter = productsQuantityAdapter

        products_quantity_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedQuantity = position+1
            }
        }
        )
    }



    private fun fillSpinnerProducts() {

        val productsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context!!.applicationContext,
            com.example.salaoluciana.R.layout.support_simple_spinner_dropdown_item,
            products
        )

        productsAdapter.setDropDownViewResource(com.example.salaoluciana.R.layout.support_simple_spinner_dropdown_item)
        products_spinner.adapter = productsAdapter

        products_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                selectedProductId = i
                Log.i("asd",this@BottomSheetAddProduct.products_list[i].name)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        })


    }

    interface BottomSheetListener{
        fun onOptionClick(text: String)
    }

}