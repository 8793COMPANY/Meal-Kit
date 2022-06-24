package com.corporation8793.mealkit.esf_wp.rest.data

/**
 * 리뷰 data class
 * @author  두동근
 * @param   product_id              리뷰할 제품의 id
 * @param   review                  리뷰 내용 (없으면 공백 한칸도 가능)
 * @param   reviewer                리뷰어 first_name
 * @param   reviewer_email          리뷰어 email
 * @param   rating                  별점 ([Int]값 - 1, 2, 3, 4, 5)
 * @param   verified                true - default
 */
data class Review(val product_id : String,
                  val review : String,
                  val reviewer : String,
                  val reviewer_email : String,
                  val rating : String,
                  val verified : Boolean = true)