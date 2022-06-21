package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.data.DisableProduct
import com.corporation8793.mealkit.esf_wp.rest.data.Store
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import org.junit.Assert
import org.junit.Test

class storeList {
    @Test
    fun storeList() {
        val boardRepository = BoardRepository()

        println("====== storeList     ======")

        println("------ listAllStore() -----")
        val listAllStoreResponse = boardRepository.listAllStore()
        println("listAllStore : ${listAllStoreResponse.first}, ${listAllStoreResponse.second}")

        // 물품 필터링
        println("------ Filtering     -----")
        val notSaleStore = listAllStoreResponse.second?.filter { it.acf.disable_product.contains(DisableProduct("유기농두부샐러드")) }
        println("유기농두부샐러드 안파는 가게들 : ")
        if (notSaleStore != null) {
            for ((i, ns) in notSaleStore.withIndex()) {
                println("${i+1}. ${ns.title.rendered}")
            }
        }

        println("====== EndTest       ======")
    }
}