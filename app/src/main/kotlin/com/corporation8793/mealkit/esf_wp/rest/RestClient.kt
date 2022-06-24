package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce.Board4BaService
import com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce.BoardService
import com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce.NonceService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

/**
 * [Retrofit] 사용을 위한 [object] 클래스
 * @author  두동근
 */
object RestClient {
    /**
     * Static IP of AWS Server (Eat-So-Fresh 밀키트)
     */
    private const val baseUrl = "http://13.209.29.68/"
    /**
     * CONSUMER KEY
     */
    private const val CONSUMER_KEY = "ck_c293912d956a0a48e86e8682af5e993c591d72fe"
    /**
     * CONSUMER SECRET
     */
    private const val CONSUMER_SECRET = "cs_5668293b63987df1626bf652f408c1fec67498c2"


    /**
     * Some 카테고리
     */
    const val CATEGORY_RECOMMEND = "0"

    /**
     * 상품 카테고리(미분류)
     */
    const val PRODUCT_NULL = "50"
    /**
     * 상품 카테고리(육지)
     */
    const val PRODUCT_LAND = "52"
    /**
     * 상품 카테고리(바다)
     */
    const val PRODUCT_SEA = "53"
    /**
     * 상품 카테고리(산)
     */
    const val PRODUCT_MOUNTAIN = "54"
    /**
     * 상품 카테고리(해외)
     */
    const val PRODUCT_OVERSEAS = "55"
    /**
     * 레시피 카테고리(고객)
     */
    const val RECIPE_CUSTOMER = "58"
    /**
     * 레시피 카테고리(베스트)
     */
    const val RECIPE_BEST = "59"
    /**
     * 포인트 카테고리(로그)
     */
    const val POINT_LOG = "60"

    /**
     * OAuth 1.0 Consumer
     */
    private val consumer = OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
    /**
     * OAuth 1.0 Client
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(SigningInterceptor(consumer))
        .build()
    /**
     * baseUrl 정보로 초기화된 [Retrofit]
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    /**
     * baseUrl 정보로 초기화된 [Retrofit]_ba
     */
    private val retrofit_ba = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * [retrofit]의 [NonceService]
     */
    val nonceService = retrofit.create(NonceService::class.java)
    /**
     * [retrofit]의 [BoardService]
     */
    val boardService = retrofit.create(BoardService::class.java)
    /**
     * [retrofit]의 [BoardService]
     */
    val board4BaService = retrofit_ba.create(Board4BaService::class.java)
}