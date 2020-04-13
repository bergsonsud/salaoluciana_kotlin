import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment() {
    private var bottomSheet: View? = null
    private var bottomSheetPeekHeight = 0
    override fun getTheme(): Int {
        return com.example.salaoluciana.R.style.BaseBottomSheetDialog
    }

    @Nullable
    fun onCreateView2(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater
            .inflate(com.example.salaoluciana.R.layout.bottom_sheet_add_product, container, false)
        //bottomSheet = view.findViewById(R.id.contentBottomSheet)
        // 86dp

        return view
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(com.example.salaoluciana.R.layout.bottom_sheet_add_product,container,false)
    }

    override fun onResume() {
        super.onResume()
        setUpBottomSheet()
    }

    private fun setUpBottomSheet() {
        var bottomSheetBehavior = BottomSheetBehavior.from(this.view)

        //bottomSheetBehavior.setPeekHeight(bottomSheetPeekHeight)
        bottomSheetBehavior.peekHeight = bottomSheet!!.height

        bottomSheetBehavior.setHideable(false);

        var childLayoutParams = bottomSheet!!.layoutParams
        var displayMetrics = DisplayMetrics();

        requireActivity()
            .getWindowManager()
            .getDefaultDisplay()
            .getMetrics(displayMetrics);

        childLayoutParams.height = displayMetrics.heightPixels;

        this.view!!.layoutParams = childLayoutParams
    }

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }
}