package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Test

class pointManage {
    @Test
    fun pointManage() {
        // basicAuth 인증정보 생성
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        // 저장소 초기화
        val boardRepository = BoardRepository()

//        println("====== productList     ======")
//
//        println("------ listAllProduct() -----")
//        val listAllProductResponse = boardRepository.listAllProduct()
//        println("listAllStore : ${listAllProductResponse.first}, ${listAllProductResponse.second}\n")
//
//        // 상품 리스트 정리
//        println("------ OrderResult      -----")
//        for (pr in listAllProductResponse.second!!) {
//            println("상품 카테고리 : ${pr.categories.first().name}")
//            println("상품명 : ${pr.name} | (주문 id : ${pr.id})")
//            println("별점 (5.00) : ${pr.average_rating}")
//            println("상품 이미지 URL : ${pr.images.first().src}")
//            println("상품 세일 기간 : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
//            println("상품가격 : ${pr.price}원")
//            println("재고정보 : ${pr.stock_quantity} / ${pr.acf.total_stock}개")
//            print("좋아요정보 : ♥ ")
//            when(pr.acf.product_likes !is Boolean) {
//                true -> {
//                    var pl = pr.acf.product_likes as ArrayList<Int>
//                    for ((i, p) in pl.withIndex()) {
//                        pl[i] = p
//                    }
//                    println("${pl.size}")
//                    println("좋아요 누른 사람들 (id) : $pl")
//                }
//                false -> println("0")
//            }
//            println("-----------------------------")
//        }
//        println("-----------------------------\n")
//
//        println("====== EndTest         ======")
    }
}