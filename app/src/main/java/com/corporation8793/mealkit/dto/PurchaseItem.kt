package com.corporation8793.mealkit.dto

data class PurchaseItem(
        val address_type : String,
        val img : String,
        val id: String,
        val product_id: String,
       val date : String,
       val shop_name : String,
       val kit_name : String,
       val kit_price : String,
       val count :  String,
       val status:  String,
)
