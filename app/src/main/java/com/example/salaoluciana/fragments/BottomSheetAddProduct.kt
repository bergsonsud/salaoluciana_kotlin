import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.salaoluciana.R
import com.example.salaoluciana.interfaces.BottomSheetInterface
import com.example.salaoluciana.models.Product
import com.example.salaoluciana.services.ProductService
import com.example.salaoluciana.util.formataParaBr
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_customer_content.*
import kotlinx.android.synthetic.main.bottom_sheet_add_product_content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BottomSheetAddProduct(var bottomSheetInterface: BottomSheetInterface,
                            var products_list : List<Product>,
                            var products : List<String>) : BottomSheetDialogFragment(),CoroutineScope {

    private var mBottomSheetListener: BottomSheetListener?=null
    var selectedQuantity : Int? = null
    var selectedProductId : Int? = null
    var selectedPosition : Int? = null
    lateinit var productSavedList : List<Product>

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_product, container, false)
    }


    override fun onStart() {
        super.onStart()
        fillSpinnerProductQuantity()
        setupAddButton()
        setupNewProduct()
        setupAutocompleteDb()
    }



    private fun setupAddButton() {
        add_product_button.setOnClickListener {
            bottomSheetInterface.AddProduct(products_list[selectedProductId!!],selectedQuantity!!)
        }
    }


    private fun setupNewProduct() {
        add_new_button.setOnClickListener {
            bottomSheetInterface.NewProduct()
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


    private fun setupAutocompleteDb(){
        var adapter = ArrayAdapter<String>(context!!,android.R.layout.simple_list_item_1,
            listOf())

        autocompleteTextProduct.setAdapter(adapter)

        autocompleteTextProduct.addTextChangedListener(object : TextWatcher{

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                launch {
                    withContext(IO){
                        products = ProductService.instance.getProductsName(s)
                        if (products.size>0) {
                            products_list = ProductService.instance.getProducts(s)
                        }
                    }
                    withContext(Dispatchers.Main){
                        adapter.clear()
                        if (products.size >0){

                            products_list.map {
                                adapter.add(it.name +" "+it.value.formataParaBr())
                            }
                            adapter.notifyDataSetChanged()
                        }


                    }

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })

        autocompleteTextProduct.setOnItemClickListener { parent, view, position, id ->
            val vo = parent.getItemAtPosition(position)
            selectedPosition = position
            selectedProductId = position
        }




    }



    interface BottomSheetListener{
        fun onOptionClick(text: String)
    }

}