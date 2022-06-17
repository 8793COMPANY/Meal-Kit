package com.corporation8793.mealkit.fragment.my

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.RatingDialog
import com.corporation8793.mealkit.adapter.PurchaseAdapter
import com.corporation8793.mealkit.decoration.KitDecoration
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.dto.MyItem
import com.corporation8793.mealkit.dto.PurchaseItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurchaseDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurchaseDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<PurchaseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_purchase_details, container, false)

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3.8).toInt()

        val purchase_list = view.findViewById<RecyclerView>(R.id.purchase_list)

        val adapter = PurchaseAdapter(parentFragmentManager,activity,context,height,findNavController())

        purchase_list.adapter = adapter

        val lm = LinearLayoutManager(context)
        purchase_list.layoutManager = lm



        datas.apply {
            datas.clear()
            add(PurchaseItem("2022.06.14 17:30","[요거트가게]","바질시금치스프","15,900","2","배송준비중"))
            add(PurchaseItem("2022.06.14 17:30","[요거트가게]","바질시금치스프","15,900","2","배송준비중"))
            add(PurchaseItem("2022.06.14 17:30","[요거트가게]","바질시금치스프","15,900","2","배송준비중"))

            adapter.datas = datas
            adapter.notifyDataSetChanged()
        }

        var divider = KitDecoration(20)
        purchase_list.addItemDecoration(divider)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PurchaseDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PurchaseDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}