package com.corporation8793.mealkit.dto

data class KitItem(
        val id: String,
        val img : String,
       val date_on_sale_from : String,
       val date_on_sale_to : String,
       val category : String,
       val name : String,
       val price : String,
       val stock_quantity : String,
        val total_stock : String,
        val like : Boolean,
        val short_description : String,
)
