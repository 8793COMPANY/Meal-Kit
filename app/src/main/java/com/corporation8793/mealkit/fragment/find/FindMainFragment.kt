package com.corporation8793.mealkit.fragment.find

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.activity.FindActivity
import com.corporation8793.mealkit.adapter.FindAdapter
import com.corporation8793.mealkit.dto.BestItem
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
class FindMainFragment() : Fragment() {
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
        var view = inflater.inflate(R.layout.fragment_find_main, container, false)
        var kindOfSearch = view.findViewById<TabLayout>(R.id.kind_of_find)
        var viewpager = view.findViewById<ViewPager2>(R.id.viewpager)
        viewpager.adapter = FindAdapter(requireActivity())
        viewpager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

//        if (intent.getStringExtra("title").equals("비밀번호"))
//            binding.viewpager.setCurrentItem(1,false)

//
        val tabName = arrayOf<String>("아이디 찾기","비밀번호 찾기")


        TabLayoutMediator(kindOfSearch, viewpager) { tab, position ->
            tab.text = tabName[position].toString()

        }.attach()

        kindOfSearch.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                (activity as FindActivity).changeTitle(tab!!.text.toString())

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        Log.e("check",(activity as FindActivity).getFind().toString())
        var check = (activity as FindActivity).getFind()

//        if(check == 1)
//            viewpager.setCurrentItem(1,false)
        if(check == 1) {
            viewpager.post {
                viewpager.setCurrentItem(1,false)
            }
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
                FindPwFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}