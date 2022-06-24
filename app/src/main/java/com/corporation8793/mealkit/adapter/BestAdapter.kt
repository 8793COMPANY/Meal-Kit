package com.corporation8793.mealkit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.fragment.BestFragmentDirections
import com.corporation8793.mealkit.fragment.HomeFragmentDirections

class BestAdapter (private val context: Context?, val height : Int, val color : Int, val controller : NavController) : RecyclerView.Adapter<BestAdapter.ViewHolder>() {
    var datas = mutableListOf<BestItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.best_list_itemview,parent,false)
        view.layoutParams.height = height
//        view.findViewById<ImageView>(R.id.herb_event_img).clipToOutline = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val kitImg: ImageView = itemView.findViewById(R.id.best_kit_img)
        private val background: ConstraintLayout = itemView.findViewById(R.id.background)
        private val kit_like_btn  = itemView.findViewById<ImageView>(R.id.kit_like_btn)
        private val ranking: TextView = itemView.findViewById(R.id.ranking)
        private val kit_name: TextView = itemView.findViewById(R.id.best_kit_name)
        private val kit_price: TextView = itemView.findViewById(R.id.best_kit_price)

        fun bind(item: BestItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true
            ranking.text = item.ranking

            kit_name.text = item.name
            kit_price.text = item.price


            Glide.with(context!!).load(item.img).into(kitImg)
            kit_like_btn.isSelected = item.like;



            itemView.setOnClickListener{
                val action = BestFragmentDirections.actionBestToRecipeFragment()
                var bundle =bundleOf("id" to item.id,"like" to item.like)
                controller.navigate(R.id.action_best_to_recipeFragment,bundle)
            }

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)




//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}