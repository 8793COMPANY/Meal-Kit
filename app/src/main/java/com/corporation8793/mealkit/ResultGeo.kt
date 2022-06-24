package com.corporation8793.mealkit

import com.google.gson.annotations.SerializedName

data class ResultGeo(
        var status : String,
        var addresses : List<Result_geo>
)

data class Result_geo(
    val roadAddress : String,
    val x : Double,
    val y : Double,

)



