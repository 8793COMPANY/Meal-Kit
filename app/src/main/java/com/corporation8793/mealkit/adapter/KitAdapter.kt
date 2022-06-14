package com.corporation8793.mealkit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.fragment.HomeFragmentDirections


class KitAdapter (private val context: Context?, val height : Int, val color : Int, val controller : NavController) : RecyclerView.Adapter<KitAdapter.ViewHolder>() {
    var datas = mutableListOf<KitItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.kit_list_itemview,parent,false)
        view.layoutParams.height = height
        Log.e("viewholder","in")
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
        private val likeBtn: ImageView = itemView.findViewById(R.id.like_btn)
        private val date: TextView = itemView.findViewById(R.id.group_purchase_date)
        private val shop_category: TextView = itemView.findViewById(R.id.shop_category)
        private val kit_name: TextView = itemView.findViewById(R.id.kit_name)
        private val kit_price: TextView = itemView.findViewById(R.id.kit_price)
        private val remaining_count : TextView = itemView.findViewById(R.id.remaining_count)

        fun bind(item: KitItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true
            date.text = item.date
            shop_category.text = item.category
            shop_category.setTextColor(color)
            kit_name.text = item.name
            kit_price.text = item.price
            remaining_count.text = item.remaining_count

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)


            itemView.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeToKitDetailFragment3()
                controller.navigate(action)
            }

            likeBtn.setOnClickListener {
                if (likeBtn.isSelected){
                    likeBtn.isSelected = false
                    likeBtn.setBackgroundResource(R.drawable.kit_like_btn_off)
                }else{
                    likeBtn.isSelected = true
                    likeBtn.setBackgroundResource(R.drawable.kit_like_btn_on)
                }
            }


//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}