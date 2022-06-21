package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.data.DisableProduct
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Test

class productList {
    @Test
    fun productList() {
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)

        println("====== storeList     ======")

        println("------ listAllProduct() -----")
        val listAllProductResponse = boardRepository.listAllProduct()
        println("listAllStore : ${listAllProductResponse.first}, ${listAllProductResponse.second}")

        // 물품 필터링
//        println("------ Filtering     -----")
//        val notSaleStore = listAllStoreResponse.second?.filter { it.acf.disable_product.contains(
//            DisableProduct("유기농두부샐러드")
//        ) }
//        println("유기농두부샐러드 안파는 가게들 : ")
//        if (notSaleStore != null) {
//            for ((i, ns) in notSaleStore.withIndex()) {
//                println("${i+1}. ${ns.title.rendered}")
//            }
//        }

        println("====== EndTest       ======")
    }
}