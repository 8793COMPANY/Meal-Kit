package com.corporation8793.itsofresh.esf_wp.rest.data

/**
 * updateOrderBody data class
 * @author  두동근
 * @param   status             주문 상태 변경 값
 * * request-cancel : 결제 취소 대기 중 (취소요청)
 * * completed : 완료됨 (구매확정)
 * * 그 외 다른 Order 상태값 사용 가능
 */
data class updateOrderBody(val status : String)
