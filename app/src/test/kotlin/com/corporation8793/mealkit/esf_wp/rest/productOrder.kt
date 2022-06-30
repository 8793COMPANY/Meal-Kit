package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Test

class productOrder {
    @Test
    fun productOrder() {
        // basicAuth 인증정보 생성
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        // 저장소 초기화
        val boardRepository = BoardRepository()

        println("====== productOrder    ======")

        println("------ makeOrder()      -----")
        // 주문 템플릿 생성
        // 배달(택배)주문은 shipping_lines 추가, is_parcel 값 1
        // 방문포장주문은 shipping_lines 추가없이 is_parcel 값 0
        val myOrder = Order(
            id = null,
            date_created = null,
            customer_id = "14",
            billing = Billing("김팔칠","광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
            shipping = Shipping("김팔칠","광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
            line_items = listOf(
                LineItems(name = null, product_id = "1513", quantity = "2", total = "18900")
            ),
            shipping_lines = listOf(
                ShippingLines()
            ),
            meta_data = listOf(
                OrderMeta(id = null, key = "store_name", value = "AST스토어"),
                OrderMeta(id = null, key = "store_address", value = "광주광역시 남구 북계천로 95"),
                OrderMeta(id = null, key = "order_point", value = "189"),
                OrderMeta(id = null, key = "is_parcel", value = 1)
            )
        )
        val myOrder2 = Order(
            id = null,
            date_created = null,
            customer_id = "14",
            billing = Billing("김팔칠","광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
            shipping = Shipping("김팔칠","광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
            line_items = listOf(
                LineItems(name = null, product_id = "1513", quantity = "2", total = "18900")
            ),
            meta_data = listOf(
                OrderMeta(id = null, key = "store_name", value = "AST스토어"),
                OrderMeta(id = null, key = "store_address", value = "광주광역시 남구 북계천로 95"),
                OrderMeta(id = null, key = "order_point", value = "189"),
                OrderMeta(id = null, key = "is_parcel", value = 0)
            )
        )
        // 주문 시작
        //val makeOrderResponse = boardRepository.makeOrder(myOrder)
        val makeOrderResponse = boardRepository.makeOrder(myOrder2)
        // 주문 응답
        println("productOrder : ${makeOrderResponse.first}, ${makeOrderResponse.second}\n")

        // 주문 결과 정리
        println("------ OrderResult      -----")
        println("-- 결제가 완료되었습니다! ---")
        println("주문번호 : ${makeOrderResponse.second?.id}")
        println("상호명 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "store_name" }?.first()?.value}")
        println("주문내역 : ${makeOrderResponse.second?.line_items?.first()?.name}|${makeOrderResponse.second?.line_items?.first()?.quantity}개")
        println("결제금액 : ${makeOrderResponse.second?.line_items?.first()?.total}")
        println("적립금 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "order_point" }?.first()?.value}")
        println("-----------------------------\n")

        println("====== EndTest         ======")
    }
}