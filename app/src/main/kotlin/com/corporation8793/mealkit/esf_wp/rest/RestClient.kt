package com.corporation8793.mealkit.esf_wp.rest

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
     * @see     <a href="http://13.209.29.68/">Eat-So-Fresh 밀키트</a>
     */
    private const val baseUrl = "http://13.209.29.68/"
    /**
     * CONSUMER KEY
     * @see     <a href="https://woocommerce.github.io/woocommerce-rest-api-docs/#authentication">authentication</a>
     */
    private const val CONSUMER_KEY = "ck_c293912d956a0a48e86e8682af5e993c591d72fe"
    /**
     * CONSUMER SECRET
     * @see     <a href="https://woocommerce.github.io/woocommerce-rest-api-docs/#authentication">authentication</a>
     */
    private const val CONSUMER_SECRET = "cs_5668293b63987df1626bf652f408c1fec67498c2"


    /**
     * Some 카테고리
     * @see     <a href="http://3.37.133.132/category/recommend/">Eat-So-Fresh 밀키트 - Some 카테고리</a>
     */
    const val CATEGORY_RECOMMEND = "0"


    /**
     * OAuth 1.0 Consumer
     * @see     <a href="https://github.com/pakerfeldt/okhttp-signpost">okhttp-signpost</a>
     */
    private val consumer = OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
    /**
     * OAuth 1.0 Client
     * @see     <a href="https://github.com/pakerfeldt/okhttp-signpost">okhttp-signpost</a>
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
     * [retrofit]의 [NonceService]
     */
    val nonceService = retrofit.create(NonceService::class.java)!!
    /**
     * [retrofit]의 [BoardService]
     */
    val boardService = retrofit.create(BoardService::class.java)
}