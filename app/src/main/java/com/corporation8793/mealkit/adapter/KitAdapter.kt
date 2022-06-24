package com.corporation8793.mealkit.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.os.persistableBundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
//        Log.e("in datas",datas[position].date)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val kitImg: ImageView = itemView.findViewById(R.id.kit_img)
        private val likeBtn: ImageView = itemView.findViewById(R.id.like_btn)
        private val date: TextView = itemView.findViewById(R.id.kit_shop_name)
        private val shop_category: TextView = itemView.findViewById(R.id.kit_name)
        private val kit_name: TextView = itemView.findViewById(R.id.kit_price)
        private val kit_price: TextView = itemView.findViewById(R.id.shipping_status)
        private val remaining_count : TextView = itemView.findViewById(R.id.remaining_count)
        private val total_stock : TextView = itemView.findViewById(R.id.total)

        fun bind(item: KitItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true
            Glide.with(context!!).load(item.img).into(kitImg)
            date.text = item.date_on_sale_from.split("T")[0]+"~"+item.date_on_sale_to.split("T")[0]
            shop_category.text = item.category
            shop_category.setTextColor(color)
            kit_name.text = item.name
            kit_price.text = item.price+"원"
            remaining_count.text = item.stock_quantity
            total_stock.text = "/"+item.total_stock+"개"

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)


            itemView.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeToKitDetailFragment3()
                val bundle = bundleOf(
                        "id" to item.id,
                        "category" to item.category,
                        "name" to item.name,
                        "price" to item.price,
                        "img" to item.img,
                        "stock" to item.stock_quantity,
                        "like" to item.like)
                controller.navigate(R.id.action_home_to_kitDetailFragment3,bundle)
            }

            likeBtn.isSelected = item.like;



//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }



}