package com.corporation8793.mealkit.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.activity.FindActivity
import com.corporation8793.mealkit.dto.RecipeItem
import com.corporation8793.mealkit.dto.ShopItem
import com.corporation8793.mealkit.fragment.HomeFragmentDirections
import com.corporation8793.mealkit.fragment.shop.RegionSearchFragmentDirections
import com.corporation8793.mealkit.fragment.shop.ShopListFragmentDirections
import java.util.*


class ShopAdapter ( private val context: Context?, val height : Int, val color : Int, val controller : NavController) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    var datas = mutableListOf<ShopItem>()
    var alldatas = mutableListOf<ShopItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.shop_list_itemview,parent,false)
        view.layoutParams.height = height
        Log.e("viewholder","in")
//        view.findViewById<ImageView>(R.id.herb_event_img).clipToOutline = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shopImg: ImageView = itemView.findViewById(R.id.shop_img)
        private val shopName: TextView = itemView.findViewById(R.id.shop_name)
        private val shopAddress: TextView = itemView.findViewById(R.id.shop_address)


        fun bind(item: ShopItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true
            shopName.text = item.name
            shopAddress.text = item.address

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)



            itemView.setOnClickListener{
                var bundle = bundleOf("shop_name" to item.name,
                        "address" to item.address,
                "id" to item.id)
                controller.navigate(R.id.action_map_screen,bundle)

            }



//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }


    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        datas.clear()
        if (charText.length == 0) {
            datas.addAll(alldatas)
            Log.e("asda","Asda");
        } else {
            Log.e("alldataCount",alldatas.count().toString());
            for (item: ShopItem in alldatas) {
                Log.e("item.name",item.name);
                val name =item.name
                if (name.toLowerCase().contains(charText)) {
                    datas.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

}