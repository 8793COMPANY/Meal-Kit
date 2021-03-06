package com.corporation8793.itsofresh.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.dto.PurchaseItem
import com.corporation8793.itsofresh.payment.CompleteOrdersActivity

class PurchaseAdapter (val activity: Activity?,private val context: Context?, val height : Int) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {
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
        private val kit_quantity = itemView.findViewById<TextView>(R.id.kit_quantity)
//        private val kit_score_btn = itemView.findViewById<TextView>(R.id.kit_score_btn)

        fun bind(item: PurchaseItem) {
            Log.e("item img",item.img)
            if (item.img != "0")
                Glide.with(context!!).load(item.img).into(kitImg)

            purchasing_date.setText(item.date)
            kit_shop_name.setText(item.shop_name)
            kit_name.setText(item.kit_name)
            kit_price.setText(item.kit_price+"???")
            shipping_status.setText(item.status)
            kit_quantity.setText(item.count+"???")

            order_details_btn.setOnClickListener{
                var intent = Intent(activity, CompleteOrdersActivity::class.java)
                intent.putExtra("type","check")
                intent.putExtra("address_type",item.address_type)
                intent.putExtra("id",item.id)
                intent.putExtra("product_id",item.product_id)
                intent.putExtra("shop_name",item.shop_name)
                intent.putExtra("name",item.kit_name)
                intent.putExtra("quantity",item.count)
                intent.putExtra("price",item.kit_price)
                Log.e("point",item.kit_price.toInt().div(100).toString())
                intent.putExtra("order_point",(item.kit_price.toInt()).div(100).toString())
                intent.putExtra("paid_point",item.paid_point)
                intent.putExtra("address",item.address)
                intent.putExtra("payment_way",item.payment_way)
                activity!!.startActivity(intent)
            }

            itemView.setOnClickListener {
                Log.e("position",adapterPosition.toString())
            }

//            kit_score_btn.setOnClickListener{
//
////                * @author  ?????????
////                * @param   product_id              ????????? ????????? id
////                * @param   review                  ?????? ?????? (????????? ?????? ????????? ??????)
////                * @param   reviewer                ????????? first_name
////                * @param   reviewer_email          ????????? email
////                * @param   rating                  ?????? ([Int]??? - 1, 2, 3, 4, 5)
////                * @param   verified                true - default
//
//                val dialog = RatingDialog(activity!!,item.product_id)
//                dialog.show(parentFragmentManager!!,"hello")
//
//
//            }


        }
    }



}