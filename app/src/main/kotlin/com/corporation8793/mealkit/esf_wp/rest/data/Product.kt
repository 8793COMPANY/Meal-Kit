package com.corporation8793.mealkit.esf_wp.rest.data
/**
 * 상품 data class
 * @author  두동근
 * @param   id                      상품의 고유 번호(Product ID)
 * @param   name                    상품명
 * @param   description             상품의 상세 설명 (레시피)
 * @param   short_description       상품의 요약 설명
 * @param   price                   상품의 가격
 * @param   date_on_sale_from       상품의 세일 시작일
 * @param   date_on_sale_to         상품의 세일 마감일
 * @param   stock_quantity          상품의 현재 재고 개수
 * @param   average_rating          상품의 평점
 * @param   categories              [Categories] 클래스
 * @param   images                  [Images] 클래스
 * @param   meta_data               [ProductMetadata] 클래스
 */
data class Product(val id : String,
                   val name : String,
                   val description : String,
                   val short_description : String,
                   val price : String,
                   val date_on_sale_from : String,
                   val date_on_sale_to : String,
                   val stock_quantity : String,
                   val average_rating : String,
                   val categories : Array<Categories>,
                   val images : Array<Images>,
                   val meta_data : Array<ProductMetadata>)
/**
 * [Product.categories]
 * @author  두동근
 * @param   id          상품 카테고리의 고유 번호(PC ID)
 * @param   name        상품 카테고리의 이름
 * @param   slug        상품 카테고리의 영문이름
 */
data class Categories(val id : String, val name : String, val slug : String)
/**
 * [Product.images]
 * @author  두동근
 * @param   src          상품 이미지의 HTTP URL
 */
data class Images(val src : String)
/**
 * [Product.meta_data]
 * @author  두동근
 * @param   id          id
 * @param   key         key ("total_stock, product_likes")
 * @param   value       value (value of total_stock, uid of product_likes)
 */
data class ProductMetadata(val id : Int?, val key : String?, val value : String?)