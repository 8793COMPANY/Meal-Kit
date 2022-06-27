package com.corporation8793.mealkit.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.adapter.HomeViewAdapter
import com.corporation8793.mealkit.service.PedometerService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val address = view.findViewById<TextView>(R.id.address)
        val viewPager = view.findViewById<ViewPager2>(R.id.kit_list)
        val tabLayout = view.findViewById<TabLayout>(R.id.kit_category)


        var pedometerHome=view.findViewById<View>(R.id.pedometer)
        var currentTimeText=pedometerHome.findViewById<TextView>(R.id.current_date);
        var total_stepText = pedometerHome.findViewById<TextView>(R.id.total_step);
        var finish_step = pedometerHome.findViewById<TextView>(R.id.finish_step);



        val dt = Date()
        val calendar = Calendar.getInstance()
        calendar.setTime(dt);
        val dayOfWeekNumber = calendar[Calendar.DAY_OF_WEEK]

        val full_sdf = SimpleDateFormat("yyyy.MM.dd")
        currentTimeText.setText(full_sdf.format(dt).toString()+getCurrentWeek(dayOfWeekNumber))


        total_stepText.setText(PedometerService.getStep().toString())
        if(PedometerService.getStep() <3000){
            finish_step.setText("/3000보")
        }else if(PedometerService.getStep() <5000){
            finish_step.setText("/5000보")
        }else{
            finish_step.setText("/10000보")
        }


        address.setText(MainApplication.instance.user.billing.address_1)


        viewPager.apply {
            adapter = HomeViewAdapter(requireParentFragment())
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

//
        val tabName = arrayOf<String>("육지","바다"," 산","해외")
        val imageResId = intArrayOf(
                R.drawable.land_icon,
                R.drawable.sea_icon,
                R.drawable.mount_icon,
                R.drawable.overseas_icon)
        val textColor = intArrayOf(
                R.color.category_land_color,
                R.color.category_sea_color,
                R.color.category_mount_color,
                R.color.category_overseas_color)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabName[position].toString()
            tab.setCustomView(createTab(tabName[position],imageResId[position]))
        }.attach()

        tabLayout.tabRippleColor = null

        //탭이 선택되었을 때, 뷰페이저가 같이 변경되도록
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                tab.customView!!.findViewById<TextView>(R.id.tabName).setTextColor(resources.getColor(textColor[tab!!.position]))
                val drawable = tabLayout.tabSelectedIndicator as GradientDrawable
                drawable.setStroke(3, ContextCompat.getColor(context!!, textColor[tab!!.position]))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.customView!!.findViewById<TextView>(R.id.tabName).setTextColor(resources.getColor(R.color.category_unselected_color))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        //여기용
        view.findViewById<View>(R.id.pedometer).setOnClickListener{
            if(MainApplication.instance.getPedometerSuccessCount("point_roulette") == 1) {
                val action = HomeFragmentDirections.actionHomeToChallengeFragment()
                findNavController().navigate(action)
            }
        }



        return view
    }

    fun createTab(tabName : String, tabIcon : Int) : View{
        val tabView: View = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)

        val tab_name = tabView.findViewById<TextView>(R.id.tabName) as TextView
        val tab_icon = tabView.findViewById<ImageView>(R.id.tabIcon) as ImageView

        tab_name.text = tabName
        if (tabName.equals("육지"))
            tab_name.setTextColor(resources.getColor(R.color.category_land_color))
        tab_icon.setBackgroundResource(tabIcon)

        return tabView
    }

    override fun onResume() {
        super.onResume()
        Log.e("onresume","in!!")
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
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


    fun getCurrentWeek(currentNumber: Int): String{
        when(currentNumber){
            1 -> return "(일)"
            2 -> return "(월)"
            3 -> return "(화)"
            4 -> return "(수)"
            5 -> return "(목)"
            6 -> return "(금)"
            7 -> return "(토)"
        }
        return "";
    }
}