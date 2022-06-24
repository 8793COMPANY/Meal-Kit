package com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce

import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody
import okhttp3.Credentials
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * 게시판(상품, 체인점)의 인터페이스
 * @author  두동근
 */
interface BoardService {
    /**
     * 전체 상품을 검색합니다.
     * @author  두동근
     */
    @GET("wp-json/wc/v3/products")
    fun listAllProduct(@Query("category") category : String? = "") : Call<List<Product>>
    /**
     * 전체 체인점을 검색합니다.
     * @author  두동근
     */
    @GET("wp-json/wp/v2/posts")
    fun listAllStore(@Query("categories") categories : String = "56") : Call<List<Store>>
    /**
     * 상품을 주문합니다.
     * @author  두동근
     */
    @POST("wp-json/wc/v3/orders")
    fun makeOrder(@Body order: Order) : Call<Order>
}