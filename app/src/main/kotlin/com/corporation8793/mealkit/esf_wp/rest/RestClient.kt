package com.corporation8793.mealkit.esf_wp.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
     * Some 카테고리
     * @see     <a href="http://3.37.133.132/category/recommend/">Eat-So-Fresh 밀키트 - Some 카테고리</a>
     */
    const val CATEGORY_RECOMMEND = "0"

    /**
     * 산야초 마을 정보로 초기화된 [Retrofit]
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * [retrofit]의 [NonceService]
     */
    //val nonceService = retrofit.create(NonceService::class.java)!!
    /**
     * [retrofit]의 [BoardService]
     */
    //val boardService = retrofit.create(BoardService::class.java)
}