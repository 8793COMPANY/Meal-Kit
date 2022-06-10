package com.corporation8793.mealkit.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.BestAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.BestItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BestFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<BestItem>()

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
        var view = inflater.inflate(R.layout.fragment_best, container, false)


        val kit_list = view.findViewById<RecyclerView>(R.id.best_kit_list)



        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3.5).toInt()

        var bestAdapter = BestAdapter(context, height, resources.getColor(R.color.category_land_color))
        kit_list.adapter = bestAdapter

        val lm = GridLayoutManager(context,2)
        kit_list.layoutManager = lm

        var divider = BestDecoration(20)
        kit_list.addItemDecoration(divider)

        datas.apply {
            add(BestItem("0","유기농두부샐러드","12,000원","1","1"))
            add(BestItem("0","과일그릭요거트보울","12,000원","1","2"))
            add(BestItem("0","헤이","12,000원","1","3"))


            bestAdapter.datas = datas
            bestAdapter.notifyDataSetChanged()
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BestFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}