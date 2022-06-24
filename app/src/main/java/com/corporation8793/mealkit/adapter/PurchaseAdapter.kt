package com.corporation8793.mealkit.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.RatingDialog
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.PurchaseItem
import com.corporation8793.mealkit.fragment.BestFragmentDirections
import com.corporation8793.mealkit.fragment.HomeFragmentDirections

class PurchaseAdapter (private val parentFragmentManager: FragmentManager?,val activity: Activity?,private val context: Context?, val height : Int, val controller : NavController) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {
    var datas = mutableListOf<PurchaseItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.purchase_details_itemview,parent,false)
        view.layoutParams.height = height
//        view.findViewById<ImageView>(R.id.herb_event_img).clipToOutline = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val kitImg: ImageView = itemView.findViewById(R.id.kit_img)
        private val purchasing_date: TextView = itemView.findViewById(R.id.purchasing_date)
        private val kit_shop_name  = itemView.findViewById<TextView>(R.id.kit_shop_name)
        private val kit_name  = itemView.findViewById<TextView>(R.id.kit_name)
        private val kit_price  = itemView.findViewById<TextView>(R.id.kit_price)
        private val shipping_status  = itemView.findViewById<TextView>(R.id.shipping_status)

        fun bind(item: PurchaseItem) {
            Log.e("bind","in")

            purchasing_date.setText(item.date)
            kit_shop_name.setText(item.shop_name)
            kit_name.setText(item.kit_name)
            kit_price.setText(item.kit_price)
            shipping_status.setText(item.status)



            itemView.setOnClickListener{

                val dialog = RatingDialog(activity!!)
                dialog.show(parentFragmentManager!!,"hello")


            }


        }
    }

//    private fun showDialog(width :Float, height : Float) {
//        val dialog = Dialog(context!!)
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
////        dialog.setCancelable(false)
//        dialog.window!!.setDimAmount(100f)
//        dialog.setContentView(R.layout.dialog_kit_score)
//        dialog.show()
//
//
////        dialog.findViewById<Button>(R.id.ok_btn).setOnClickListener{
////            dialog.dismiss()
////        }
//
//
//
//        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
////        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//
//
//    }

}