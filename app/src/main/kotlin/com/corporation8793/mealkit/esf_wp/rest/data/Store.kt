package com.corporation8793.mealkit.esf_wp.rest.data

/**
 * 체인점 data class
 * @author  두동근
 * @param   id                          체인점의 고유 번호(CP ID)
 * @param   featured_media_src_url      체인점의 메인 이미지 HTTP URL
 * @param   title                       [Title] 클래스
 * @param   content                     [Content] 클래스
 * @param   acf                         [Acf] 클래스
 */
data class Store(val id : String,
                 val featured_media_src_url : String,
                 val title : Title,
                 val content : Content,
                 val acf : Acf)
/**
 * [Store.title]
 * @author  두동근
 * @param   rendered    체인점의 상호명
 */
data class Title(val rendered : String)
/**
 * [Store.content]
 * @author  두동근
 * @param   rendered    체인점 소개글
 */
data class Content(val rendered : String)
/**
 * [Store.acf]
 * @author  두동근
 * @param   metropolitan        체인점의 광역시/도
 * @param   feature_image_2     체인점의 추가 이미지 HTTP URL
 * @param   disable_product     체인점의 미취급 상품 목록
 */
data class Acf(val metropolitan : String?,
               val feature_image_2 : String?,
               val disable_product : Array<DisableProduct>)
/**
 * [Acf.disable_product]
 * @author  두동근
 * @param   post_title      미취급 상품의 상품명
 */
data class DisableProduct(val post_title : String)