package com.corporation8793.mealkit.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.RatingDialog
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.PurchaseItem
import com.corporation8793.mealkit.fragment.BestFragmentDirections
import com.corporation8793.mealkit.fragment.HomeFragmentDirections
import com.corporation8793.mealkit.payment.CompleteOrdersActivity
import com.corporation8793.mealkit.payment.SelectProductActivity

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
        private val order_details_btn = itemView.findViewById<TextView>(R.id.order_details_btn)
        private val kit_score_btn = itemView.findViewById<TextView>(R.id.kit_score_btn)

        fun bind(item: PurchaseItem) {
            Log.e("bind","in")

            Glide.with(context!!).load(item.img).into(kitImg)

            purchasing_date.setText(item.date)
            kit_shop_name.setText(item.shop_name)
            kit_name.setText(item.kit_name)
            kit_price.setText(item.kit_price+"원")
            shipping_status.setText(item.status)

            order_details_btn.setOnClickListener{
                var intent = Intent(activity, CompleteOrdersActivity::class.java)
                intent.putExtra("type","check")
                intent.putExtra("id",item.id)
                intent.putExtra("product_id",item.product_id)
                intent.putExtra("shop_name",item.shop_name)
                intent.putExtra("name",item.kit_name)
                intent.putExtra("quantity",item.count)
                intent.putExtra("price",item.kit_price)
                Log.e("point",item.kit_price.toInt().div(100).toString())
                intent.putExtra("order_point",(item.kit_price.toInt()).div(100).toString())
                intent.putExtra("address",item.status)
                activity!!.startActivity(intent)
            }

            kit_score_btn.setOnClickListener{

//                * @author  두동근
//                * @param   product_id              리뷰할 제품의 id
//                * @param   review                  리뷰 내용 (없으면 공백 한칸도 가능)
//                * @param   reviewer                리뷰어 first_name
//                * @param   reviewer_email          리뷰어 email
//                * @param   rating                  별점 ([Int]값 - 1, 2, 3, 4, 5)
//                * @param   verified                true - default

                val dialog = RatingDialog(activity!!,item.product_id)
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