package com.corporation8793.mealkit.esf_wp.rest.repository

import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.*
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

/**
 * [BoardService]의 구현 클래스
 * @author  두동근
 * @see     BoardService
 * @see     Credentials
 */
class BoardRepository {
    /**
     * 전체 상품을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Product>]
     * @param   category                    카테고리 필터링 (미지정시 전체 출력)
     * * [RestClient.PRODUCT_LAND]          육지
     * * [RestClient.PRODUCT_SEA]           바다
     * * [RestClient.PRODUCT_MOUNTAIN]      산
     * * [RestClient.PRODUCT_OVERSEAS]      해외
     * * [RestClient.PRODUCT_NULL]          미분류
     * @see     Post
     * @see     Pair
     */
    fun listAllProduct(category : String = "") : Pair<String, List<Product>?> {
        val call = RestClient.boardService.listAllProduct(category)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    fun productLikesEdit(product: Product, userId: String, action: String) : Pair<String, Product?> {
        var response : Response<Product>? = null

        return when (action) {
            "ON" -> {
                println("[좋아요 ON]")
                when(product.acf.product_likes !is Boolean) {
                    true -> {
                        var pl = product.acf.product_likes as ArrayList<Int>
                        for ((i, p) in pl.withIndex()) {
                            pl[i] = p
                        }
                        pl.remove(userId.toInt())
                        pl.add(userId.toInt())

                        response = RestClient.boardService.productLikesEdit(id = product.id,
                                likesBody = likesBody(
                                arrayOf(
                                    Meta_data(id = null, key = "product_likes",
                                        value = pl )
                                )
                            )
                        ).execute()
                    }
                    false -> {
                        var pl = arrayListOf<Int>()
                        pl.add(userId.toInt())

                        response = RestClient.boardService.productLikesEdit(id = product.id,
                            likesBody = likesBody(
                                arrayOf(
                                    Meta_data(id = null, key = "product_likes",
                                        value = pl )
                                )
                            )
                        ).execute()
                    }
                }
                return Pair(response?.code().toString(), response?.body())
            }
            "OFF" -> {
                println("[좋아요 OFF]")
                when(product.acf.product_likes !is Boolean) {
                    true -> {
                        var pl = product.acf.product_likes as ArrayList<Int>
                        for ((i, p) in pl.withIndex()) {
                            pl[i] = p
                        }
                        pl.remove(userId.toInt())

                        response = RestClient.boardService.productLikesEdit(id = product.id,
                            likesBody = likesBody(
                                arrayOf(
                                    Meta_data(id = null, key = "product_likes",
                                        value = pl )
                                )
                            )
                        ).execute()
                    }
                    false -> {
                        var pl = arrayListOf<Int>()
                        pl.add(userId.toInt())

                        response = RestClient.boardService.productLikesEdit(id = product.id,
                            likesBody = likesBody(
                                arrayOf(
                                    Meta_data(id = null, key = "product_likes",
                                        value = pl )
                                )
                            )
                        ).execute()
                    }
                }
                return Pair(response?.code().toString(), response?.body())
            }
            else -> {
                return Pair("null", null)
            }
        }
    }
    /**
     * 전체 체인점을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Store>]
     * @see     Post
     * @see     Pair
     */
    fun listAllStore() : Pair<String, List<Store>?> {
        val call = RestClient.boardService.listAllStore()

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 상품을 주문합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [Order]
     * @see     Order
     * @see     Pair
     */
    fun makeOrder(order: Order) : Pair<String, Order?> {
        val call = RestClient.boardService.makeOrder(order)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 전체 주문내역을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Order>]
     * @param   customer        고객 아이디
     * @see     Order
     * @see     Pair
     */
    fun listAllOrder(customer : String) : Pair<String, List<Order>?> {
        val call = RestClient.boardService.listAllOrder(customer)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }


    fun makeReview(review: Review) : Pair<String, Review?> {
        val call = RestClient.boardService.makeReview(review)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
}