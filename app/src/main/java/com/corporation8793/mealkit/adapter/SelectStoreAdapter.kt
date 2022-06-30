package com.corporation8793.mealkit.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.activity.WriteRecipeActivity
import com.corporation8793.mealkit.dto.ShopItem
import com.corporation8793.mealkit.payment.PayMentActivity


class SelectStoreAdapter (private val activity: Activity?,private val context: Context?, val height : Int) : RecyclerView.Adapter<SelectStoreAdapter.ViewHolder>() {
    var datas = mutableListOf<ShopItem>()
    interface OnItemClickListener{
        fun onItemClick(v:View,  pos : Int, item : ShopItem)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
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
            shopName.text = item.name
            shopAddress.text = item.address


//            itemView.setOnClickListener{
//
//            }

            Glide.with(context!!).load(item.img).into(shopImg)

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,pos,item)
                }
            }



        }
    }

}