package com.corporation8793.itsofresh.adapter

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.dto.RecipeItem
import java.util.*


class RecipeAdapter (private val context: Context?, val height : Int, val color : Int, val controller : NavController) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    var datas = mutableListOf<RecipeItem>()
    var alldatas = mutableListOf<RecipeItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_list_itemview,parent,false)
//        view.layoutParams.height = height
//        view.findViewById<ImageView>(R.id.herb_event_img).clipToOutline = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val recipeImg: ImageView = itemView.findViewById(R.id.recipe_img)
        private val userImg: ImageView = itemView.findViewById(R.id.user_img)
        private val recipe_like_btn  = itemView.findViewById<ImageView>(R.id.recipe_like_btn)
        private val recipe_name: TextView = itemView.findViewById(R.id.recipe_name)
        private val recipe_introdution: TextView = itemView.findViewById(R.id.recipe_introdution)

        fun bind(item: RecipeItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true


            recipe_name.text = item.name
            recipe_introdution.text = item.introdution

            recipe_like_btn.isSelected = item.like;

            recipe_like_btn.setOnClickListener {
                if (recipe_like_btn.isSelected) {
                    recipe_like_btn.isSelected = false
                } else {
                    recipe_like_btn.isSelected = true
                }
            }


//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
            Log.e("img",item.img)
                Glide.with(context!!).load(item.img).diskCacheStrategy(DiskCacheStrategy.ALL).into(recipeImg)
            Glide.with(context!!).load(item.user_img).into(userImg)
//            Glide.with(context!!).load(R.drawable.recipe_img_test).diskCacheStrategy(DiskCacheStrategy.ALL).into(recipeImg)

            itemView.setOnClickListener{
                var bundle = bundleOf("id" to item.id, "like" to item.like, "category" to "recipe")
                controller.navigate(R.id.action_recipe_list_to_recipeFragment,bundle)
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
            for (item: RecipeItem in alldatas) {
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