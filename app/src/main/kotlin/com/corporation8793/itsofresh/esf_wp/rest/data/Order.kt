package com.corporation8793.itsofresh.esf_wp.rest.data

/**
 * 주문 data class
 * @author  두동근
 * @param   id                  주문번호
 * @param   date_created        주문 일자
 * status - pending
 * currency - KRW
 * @param   customer_id         주문 고객의 id
 * @param   billing             [Billing] 클래스
 * @param   shipping            [Shipping] 클래스
 * @param   line_items          [LineItems] 클래스
 *
 * @param   payment_url         Payment gateways URL
 */
data class Order(val id : Int?,
                 val date_created : String?,
                 val payment_method : String? = "kcp_card",
                 val payment_method_title : String? = "NHN KCP",
                 val status : String = "pending",
                 val currency : String = "KRW",
                 val customer_id : String,
                 val billing : Billing,
                 val shipping : Shipping,
                 val line_items : List<LineItems>,
                 val shipping_lines : List<ShippingLines>? = null,
                 val meta_data : List<OrderMeta>,
                 val payment_url : String?
                 )
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
 * [Order.shipping_lines]
 * @author  두동근
 * parcel
 * @param   method_id       주문 배송 클래스 슬러그값
 * @param   method_title    주문 배송 클래스 이름
 * @param   total           배송료
 */
data class ShippingLines(val method_id : String = "parcel", val method_title : String = "배송", val total : String = "3000")
/**
 * 주문 Meta data
 * @author  두동근
 * @param   id          id
 * @param   key         key
 *  * store_name(상호명)
 *  * store_address(상호주소)
 *  * order_point(적립금)
 *  * is_parcel(플래그)
 *  * _pafw_vacc_bank_name(무통장입금 은행명)
 *  * _pafw_vacc_num(무통장입금 계좌번호)
 *  * _pafw_vacc_depositor(무통장입금 예금주)
 * @param   value       value
 * @see     [Order.meta_data]
 */
data class OrderMeta(val id : Int?,
                     val key : String,
                     val value : Any)