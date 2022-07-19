package com.corporation8793.itsofresh.dto

data class RecipeItem(
        val id : String,
       val img : String,
       val name : String,
       val introdution : String,
       val user_img : String,
       val like : Boolean,
       val like_count : String,
)
