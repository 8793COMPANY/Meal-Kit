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
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.RecipeItem
import com.corporation8793.mealkit.fragment.RecipeListFragmentDirections


class RecipeAdapter (private val context: Context?, val height : Int, val color : Int, val controller : NavController) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    var datas = mutableListOf<RecipeItem>()
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
        private val recipe_like_btn  = itemView.findViewById<ImageView>(R.id.recipe_like_btn)
        private val recipe_name: TextView = itemView.findViewById(R.id.recipe_name)
        private val recipe_introdution: TextView = itemView.findViewById(R.id.recipe_introdution)

        fun bind(item: RecipeItem) {
            Log.e("bind","in")
//            eventImg.clipToOutline = true


            recipe_name.text = item.name
            recipe_introdution.text = item.introdution

            recipe_like_btn.setOnClickListener {
                if (recipe_like_btn.isSelected){
                    recipe_like_btn.isSelected = false
                }else{
                    recipe_like_btn.isSelected = true
                }
            }

//            if (item.img == "0") {
//                kitImg.setBackgroundResource(R.color.app_basic_color)
//            }else
//                Glide.with(context!!).load(item.img).into(kitImg)

            itemView.setOnClickListener{
                val action = RecipeListFragmentDirections.actionRecipeListToRecipeFragment()
                controller.navigate(action)
            }



//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}