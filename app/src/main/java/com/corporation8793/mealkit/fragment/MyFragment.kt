package com.corporation8793.mealkit.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.MyAdapter
import com.corporation8793.mealkit.decoration.KitDecoration
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.dto.MyItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = ArrayList<MyItem>()

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
        var view = inflater.inflate(R.layout.fragment_my, container, false)

        val my_list = view.findViewById<GridView>(R.id.my_list)


        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 6.5).toInt()

        datas.apply {
            datas.clear()
            add(MyItem(R.drawable.my_event_icon,"이벤트","N",true))
            add(MyItem(R.drawable.my_purchase_details_icon,"구매내역","3",true))
            add(MyItem(R.drawable.my_shop_list_icon,"매장 공유 등록","N",false))
            add(MyItem(R.drawable.my_point_icon,"포인트","N",false))
            add(MyItem(R.drawable.my_friend_icon,"친구초대","N",false))
            add(MyItem(R.drawable.my_kakao_icon,"카카오톡 연동","N",false))

        }

        var myAdapter = MyAdapter(context,datas,findNavController())

        my_list.adapter = myAdapter

        my_list.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Log.e("check",i.toString())
            if (i == 3){
                val action = MyFragmentDirections.actionMyToPointFragment()
                findNavController().navigate(action)
            }

        })


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
                MyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}