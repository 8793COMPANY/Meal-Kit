package com.corporation8793.itsofresh.fragment.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.adapter.*
import com.corporation8793.itsofresh.dto.ShopItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopListFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<ShopItem>()
    lateinit var kindOfSearch : TabLayout
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
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_shop_list, container, false)


        kindOfSearch = view.findViewById<TabLayout>(R.id.kind_of_search)
        var viewpager = view.findViewById<ViewPager2>(R.id.viewpager)
        viewpager.adapter = SearchAdapter(activity!!)
        viewpager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

//        if (intent.getStringExtra("title").equals("????????????"))
//            binding.viewpager.setCurrentItem(1,false)

//
        val tabName = arrayOf<String>("????????????","????????????")

        kindOfSearch.tabRippleColor = null
        
        TabLayoutMediator(kindOfSearch, viewpager) { tab, position ->
            tab.text = tabName[position].toString()
        }.attach()


        kindOfSearch.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewpager.setCurrentItem(tabIndex,false)




        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tabIndex",kindOfSearch.selectedTabPosition)
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
                ShopListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}