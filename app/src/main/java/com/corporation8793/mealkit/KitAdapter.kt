package com.corporation8793.mealkit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class KitAdapter (private val context: Context?, val height : Int) : RecyclerView.Adapter<KitAdapter.ViewHolder>() {
    var datas = mutableListOf<KitItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.kit_list_itemview,parent,false)
        view.layoutParams.height = height
//        view.findViewById<ImageView>(R.id.herb_event_img).clipToOutline = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        Log.e("in datas",datas[position].date)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val kitImg: ImageView = itemView.findViewById(R.id.kit_img)
        private val date: TextView = itemView.findViewById(R.id.group_purchase_date)
        private val shop_category: TextView = itemView.findViewById(R.id.shop_category)
        private val kit_name: TextView = itemView.findViewById(R.id.kit_name)
        private val kit_price: TextView = itemView.findViewById(R.id.kit_price)
        private val remaining_count : TextView = itemView.findViewById(R.id.remaining_count)

        fun bind(item: KitItem) {
//            eventImg.clipToOutline = true
            date.text = item.date
            shop_category.text = item.category
            kit_name.text = item.name
            kit_price.text = item.price
            remaining_count.text = item.remaining_count

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)




//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}