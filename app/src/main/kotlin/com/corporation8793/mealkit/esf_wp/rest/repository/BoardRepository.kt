package com.corporation8793.mealkit.esf_wp.rest.repository

import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.Order
import com.corporation8793.mealkit.esf_wp.rest.data.Product
import com.corporation8793.mealkit.esf_wp.rest.data.Store
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.http.Body

/**
 * [BoardService]의 구현 클래스
 * @author  두동근
 * @see     BoardService
 * @see     Credentials
 * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/">WP-Posts [REST API Reference]</a>
 * @see     <a href="https://developer.wordpress.org/rest-api/reference/comments/">WP-Comments [REST API Reference]</a>
 */
class BoardRepository(val basicAuth : String) {
    /**
     * 전체 상품을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Product>]
     * @see     Post
     * @see     Pair
     */
    fun listAllProduct() : Pair<String, List<Product>?> {
        val call = RestClient.boardService.listAllProduct()

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 전체 체인점을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Store>]
     * @see     Post
     * @see     Pair
     * @see     <a href="https://developer.wordpress.org/rest-api/reference/posts/#list-posts">List Posts [REST API Reference]</a>
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
}