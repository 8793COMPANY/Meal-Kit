package com.corporation8793.mealkit.esf_wp.rest.data

/**
 * 주문 data class
 * @author  두동근
 * @param   id                  주문번호
 * @param   date_created        주문 일자
 * status - on-hold
 * currency - KRW
 * @param   customer_id         주문 고객의 id
 * @param   billing             [Billing] 클래스
 * @param   shipping            [Shipping] 클래스
 * @param   line_items          [LineItems] 클래스
 */
data class Order(val id : Int?,
                 val date_created : String?,
                 val status : String = "on-hold",
                 val currency : String = "KRW",
                 val customer_id : String,
                 val billing : Billing,
                 val shipping : Shipping,
                 val line_items : List<LineItems>,
                 val meta_data : List<OrderMeta>)
/**
 * [Order.line_items]
 * @author  두동근
 * @param   name                주문 상품명
 * @param   product_id          주문 상품의 id
 * @param   quantity            주문 상품의 수량
 * @param   total               주문 상품의 총액
 */
data class LineItems(val name : String?,
                     val product_id : String,
                     val quantity : String,
                     val total : String)
/**
 * 주문 Meta data
 * @author  두동근
 * @param   id          id
 * @param   key         key ("store_name(상호명)", "order_point(적립금)")
 * @param   value       value
 * @see     [Order.meta_data]
 */
data class OrderMeta(val id : Int?,
                     val key : String,
                     val value : Any)