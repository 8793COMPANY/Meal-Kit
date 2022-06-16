package com.corporation8793.mealkit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.PointItem

class PointAdapter (private val context: Context?, val height : Int) : RecyclerView.Adapter<PointAdapter.ViewHolder>() {
    var datas = mutableListOf<PointItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.point_list_itemview,parent,false)
        view.layoutParams.height = height
//        view.findViewById<ImageView>(R.id.herb_event_img).clipToOutline = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shop_name: TextView = itemView.findViewById(R.id.shop_name)
        private val saving_date: TextView = itemView.findViewById(R.id.saving_date)
        private val saved_money: TextView = itemView.findViewById(R.id.saved_money)

        fun bind(item: PointItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true
            shop_name.text = item.shop_name

            saving_date.text = item.saving_date
            saved_money.text = item.point

            }

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)




//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }
