package com.corporation8793.itsofresh.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.dto.PointItem

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
//            eventImg.clipToOutline = true
            shop_name.text = item.shop_name

            saving_date.text = item.saving_date
            saved_money.setText(Html.fromHtml(item.point))

            Log.e("item.point",saved_money.text.toString())
                if(saved_money.text.toString().trim().contains("–")){
                    Log.e("red","in")
                  saved_money.setTextColor(ContextCompat.getColor(context!!,R.color.red_ce2929))
                }else{
                    saved_money.setTextColor(ContextCompat.getColor(context!!,R.color.black))
                }
            }

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)




//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

