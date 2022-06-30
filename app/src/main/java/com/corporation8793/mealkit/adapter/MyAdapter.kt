package com.corporation8793.mealkit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.MyItem

class MyAdapter (private val context: Context?, private var myList : ArrayList<MyItem>, val controller : NavController) : BaseAdapter() {

    override fun getCount(): Int {
        return myList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val my : MyItem = myList[p0]
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.my_page_list_itemview, null)

        val img: ImageView = view.findViewById(R.id.about_my_list_img)
        val noti: TextView = view.findViewById(R.id.about_my_list_noti)
        val category: TextView = view.findViewById(R.id.about_my_list_type)

        if (my.name.equals("자동로그인")){
            img.isSelected = my.check
            noti.visibility = View.INVISIBLE
        }else{
            if (!my.check) {
                noti.visibility = View.INVISIBLE
            }else{
                noti.text = my.noti
            }
        }


        category.text = my.name


        img.setBackgroundResource(my.img)


        return view
    }

}