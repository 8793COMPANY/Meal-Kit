package com.corporation8793.itsofresh

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverAPI{
    @GET("v2/geocode")
    fun getPath(
            @Header("X-NCP-APIGW-API-KEY-ID") apiKeyID: String,
            @Header("X-NCP-APIGW-API-KEY") apiKey: String,
            @Query("query") query: String,
    ): Call<ResultGeo>
 
}
