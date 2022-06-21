package com.corporation8793.mealkit.esf_wp.rest.data

/**
 * 주문 data class
 * @author  두동근
 * status - on-hold 고정
 * currency - KRW 고정
 * customer_id - 입력
 * @param   id                  회원의 고유 번호(User ID)
 * @param   username            회원의 로그인 아이디
 * @param   email               회원의 이메일
 * @param   first_name          고객의 이름
 * @param   billing             [Billing] 클래스
 * @param   shipping            [Shipping] 클래스
 * arr<line_items> - product_id, quantity
 */
data class Order(val id : String)
