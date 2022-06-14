package com.corporation8793.mealkit.fragment.home

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.adapter.KitAdapter
import com.corporation8793.mealkit.decoration.KitDecoration
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.KitItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LandFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val datas = mutableListOf<KitItem>()

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
        val view = inflater.inflate(R.layout.fragment_land, container, false)

        val kit_list = view.findViewById<RecyclerView>(R.id.kit_list)



        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 6.5).toInt()

        var kitAdapter = KitAdapter(context, height, resources.getColor(R.color.category_land_color),findNavController())
        kit_list.adapter = kitAdapter

        val lm = LinearLayoutManager(context)
        kit_list.layoutManager = lm

        var divider = KitDecoration(20)
        kit_list.addItemDecoration(divider)

        datas.apply {
            add(KitItem("0","22.05.12~22.05.14","샐러드 가게","유기농두부샐러드","12,000원","17","1"))
            add(KitItem("0","22.05.12~22.05.14","스프 가게","시금치스프","8,000원","4","1"))
            add(KitItem("0","22.05.12~22.05.14","라멘 가게","매운냉라면","10,000원","12","1"))

            kitAdapter.datas = datas
            kitAdapter.notifyDataSetChanged()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LandFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LandFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}