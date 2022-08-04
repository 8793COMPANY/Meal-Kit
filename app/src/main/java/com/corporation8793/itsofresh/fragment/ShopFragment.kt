package com.corporation8793.itsofresh.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.adapter.ShopViewAdapter
import com.corporation8793.itsofresh.dto.KitItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_find_main.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<KitItem>()
    lateinit var tabLayout : TabLayout
    var tabIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (savedInstanceState != null)
            tabIndex = savedInstanceState.getInt("tabIndex")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("oncreateview","in")
        Log.e("tabIndex",tabIndex.toString())
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_shop, container, false)

        tabLayout = view.findViewById<TabLayout>(R.id.select_shop_view_way)

        val user_address = view.findViewById<TextView>(R.id.user_address)

        user_address.setText(MainApplication.instance.user.shipping.address_1)
//
        val viewPager = view.findViewById<ViewPager2>(R.id.kit_list)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = ShopViewAdapter(this)
        val imageResId = intArrayOf(
                R.drawable.shop_map_icon,
                R.drawable.shop_list_icon)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(imageResId[position])
            if(position == tabIndex)
                tab.icon!!.setTint(resources.getColor(R.color.white))
        }.attach()

        tabLayout.tabRippleColor = null

        //탭이 선택되었을 때, 뷰페이저가 같이 변경되도록

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
               tab!!.icon!!.setTint(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.icon!!.setTint(resources.getColor(R.color.app_basic_color))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.setCurrentItem(tabIndex,false)



        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tabIndex",tabLayout.selectedTabPosition)
    }

    override fun onResume() {
        super.onResume()
        Log.e("shop","re in !")
        if(tabLayout != null)
            Log.e("position", tabLayout.selectedTabPosition.toString())

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
                ShopFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}