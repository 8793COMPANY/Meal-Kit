package com.corporation8793.itsofresh.esf_wp.rest.api_interface.nonce

import com.corporation8793.itsofresh.esf_wp.rest.data.*
import retrofit2.Call
import retrofit2.http.*

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

    @GET("wp-json/wc/v3/products/{id}")
    fun retrieveOneProduct(@Path("id") id : String) : Call<Product?>
    /**
     * 상품의 좋아요를 On/Off 합니다.
     * @author  두동근
     */
    @PUT("wp-json/wc/v3/products/{id}")
    fun productLikesEdit(@Path("id") id : String,
                         @Query("_fields") _fields : String = "acf.product_likes",
                         @Body likesBody: likesBody) : Call<Product>

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
    /**
     * 전체 주문내역을 검색합니다.
     * @author  두동근
     */
    @GET("wp-json/wc/v3/orders")
    fun listAllOrder(@Query("customer") customer : String) : Call<List<Order>>

    @POST("wp-json/wc/v3/products/reviews")
    fun makeReview(@Body review: Review) : Call<Review>
}