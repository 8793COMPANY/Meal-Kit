package com.corporation8793.itsofresh.dto

data class PurchaseItem(
    val pos : Int,
    val address_type : String,
    var img : String,
    val id: String,
    val product_id: String,
    val date : String,
    val shop_name : String,
    val kit_name : String,
    val kit_price : String,
    val count :  String,
    var status:  String,
    val address:  String,
    val paid_point:  String,
    val payment_way:  String,
)
