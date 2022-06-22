package com.corporation8793.mealkit.esf_wp.rest.repository

import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.*
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import java.io.File

/**
 * [BoardService]의 구현 클래스
 * @author  두동근
 * @see     BoardService
 * @see     Credentials
 */
class BoardRepository(val basicAuth : String) {
    /**
     * 전체 상품을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Product>]
     * @param   category                    카테고리 필터링 (미지정시 전체 출력)
     * * [RestClient.PRODUCT_LAND]          육지
     * * [RestClient.PRODUCT_SEA]           바다
     * * [RestClient.PRODUCT_MOUNTAIN]      산
     * * [RestClient.PRODUCT_OVERSEAS]      해외
     * * [RestClient.PRODUCT_NULL]          미분류
     * @see     Post
     * @see     Pair
     */
    fun listAllProduct(category : String = "") : Pair<String, List<Product>?> {
        val call = RestClient.boardService.listAllProduct(category)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 전체 체인점을 검색합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [List<Store>]
     * @see     Post
     * @see     Pair
     */
    fun listAllStore() : Pair<String, List<Store>?> {
        val call = RestClient.boardService.listAllStore()

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 상품을 주문합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [Order]
     * @see     Order
     * @see     Pair
     */
    fun makeOrder(order: Order) : Pair<String, Order?> {
        val call = RestClient.boardService.makeOrder(order)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }

    /**
     * 게시물을 생성합니다.
     * @author  두동근
     * @param   title               제목
     * @param   content             내용
     * @param   categories          [RestClient]의 카테고리값 (기본값 : [RestClient.RECIPE_CUSTOMER])
     * * 고객 레시피 - [RestClient.RECIPE_CUSTOMER]
     * * 베스트 레시피 - [RestClient.RECIPE_BEST]
     * @param   featured_media      [Media.id] (기본값 : 0)
     * @return  responseCode (expected : "201")
     */
    fun createPost(title : String, content : String, categories : String =
        RestClient.RECIPE_CUSTOMER, featured_media : String? = "0") : String {

        val verifiedCategories = when (categories) {
            RestClient.RECIPE_CUSTOMER -> categories
            RestClient.RECIPE_BEST -> categories
            else -> {
                RestClient.RECIPE_CUSTOMER
            }
        }

        val call = RestClient.boardService.createPost(basicAuth, title = title, content = content,
            categories = verifiedCategories, featured_media = featured_media)

        return call.execute().code().toString()
    }

    /**
     * id([postId])가 일치하는 게시물을 검색합니다.
     * * (id가 일치하는 게시물은 1개(One)입니다.)
     * @author  두동근
     * @param   postId  게시물 id
     * @return  responseCode (expected : "200"), [Post]
     * @see     Post
     * @see     Pair
     */
    fun retrieveOnePost(postId : String) : Pair<String, Post?> {
        val call = RestClient.boardService.retrieveOnePost(id = postId)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }

    /**
     * 카테고리 내 전체 게시물을 검색합니다.
     * @author  두동근
     * @param   per_page            페이지 당 게시물 개수 (기본값 : 100)
     * @param   page                선택한 페이지 (기본값 : 1)
     * @param   order               [Post.date]기준 내림차순(desc), 오름차순(asc) (기본값 : desc)
     * @param   categories          [RestClient]의 카테고리값 (기본값 : [RestClient.RECIPE_CUSTOMER])
     * * 고객 레시피 - [RestClient.RECIPE_CUSTOMER]
     * * 베스트 레시피 - [RestClient.RECIPE_BEST]
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     */
    fun retrievePostInCategories(per_page : String = "100", page : String = "1", order : String = "desc",
                                 categories : String = RestClient.RECIPE_CUSTOMER) : Pair<String, List<Post>?> {
        val verifiedCategories = when (categories) {
            RestClient.RECIPE_CUSTOMER -> categories
            RestClient.RECIPE_BEST -> categories
            else -> {
                RestClient.RECIPE_CUSTOMER
            }
        }

        val call = RestClient.boardService.retrievePostInCategories(per_page, page, order, verifiedCategories)

        val response = call.execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 게시물을 수정합니다.
     * * id([postId])가 일치하는 게시물을 수정합니다.
     * @author  두동근
     * @param   postId  게시물 id
     * @param   title   제목
     * @param   content 내용
     * @return  responseCode (expected : "200")
     */
    fun updatePost(postId : String, title : String, content : String) : String {
        val call = RestClient.boardService.updatePost(basicAuth, id = postId, title = title, content = content)

        return call.execute().code().toString()
    }
    /**
     * 게시물을 삭제합니다.
     * * id([postId])가 일치하는 게시물을 삭제합니다.
     * @author  두동근
     * @param   postId  게시물 id
     * @return  responseCode (expected : "200")
     */
    fun deletePost(postId : String) : String {
        val call = RestClient.boardService.deletePost(basicAuth, id = postId)

        return call.execute().code().toString()
    }

    /**
     * [Post.featured_media]로 사용할 이미지를 업로드합니다.
     * @author  두동근
     * @param   file        이미지 [File]
     * @return  responseCode (expected : "201"), [Media]
     * @see     Media
     * @see     Pair
     * @see     File
     * @see     Post.featured_media
     */
    fun uploadMedia(file : File) : Pair<String, Media?> {
        val filePart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            RequestBody.create(MediaType.parse("image/*"), file)
        )

        val response = RestClient.boardService.uploadMedia(basicAuth, filePart).execute()
        println("raw data : ${response.raw()}")

        return Pair(response.code().toString(), response.body())
    }
    /**
     * id([mediaId])가 일치하는 미디어를 검색합니다.
     * * (id가 일치하는 미디어는 1개(One)입니다.)
     * @author  두동근
     * @param   mediaId  미디어 id
     * @return  responseCode (expected : "200"), [Media]
     * @see     Media
     * @see     Pair
     * @see     Post.featured_media
     */
    fun retrieveMedia(mediaId : String?) : Pair<String, Media?> {
        val response = RestClient.boardService.retrieveMedia(mediaId).execute()

        return Pair(response.code().toString(), response.body())
    }
}