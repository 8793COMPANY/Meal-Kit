package com.corporation8793.itsofresh.esf_wp.rest.data

/**
 * 고객 data class
 * @author  두동근
 * @param   id                  회원의 고유 번호(User ID)
 * @param   username            회원의 로그인 아이디
 * @param   email               회원의 이메일
 * @param   first_name          고객의 이름
 * @param   billing             [Billing] 클래스
 * @param   shipping            [Shipping] 클래스
 * @param   meta_data           [meta_data] 클래스
 */
data class Customer(val id : String,
                    val username : String,
                    val email : String,
                    val first_name : String,
                    val billing : Billing,
                    val shipping : Shipping,
                    val meta_data : List<Meta_data>)
/**
 * [Customer.billing]
 * @author  두동근
 * @param   address_1       주소
 * @param   address_2       상세주소
 * @param   postcode        우편번호
 * @param   phone           연락처
 */
data class Billing(val first_name : String,
                   val address_1 : String,
                   val address_2 : String,
                   val postcode : String,
                   val phone : String)
/**
 * [Customer.shipping]
 * @author  두동근
 * @param   address_1       주소
 * @param   address_2       상세주소
 * @param   postcode        우편번호
 * @param   phone           연락처
 */
data class Shipping(val first_name : String,
                    val address_1 : String,
                    val address_2 : String,
                    val postcode : String,
                    val phone : String)
/**
 * 고객 Meta data
 * @author  두동근
 * @param   id          id
 * @param   key         key ("recommender")
 * @param   value       value (username of recommender)
 * @see     [Customer.meta_data]
 */
data class Meta_data(val id : Int?,
                     val key : String?,
                     val value : Any?)