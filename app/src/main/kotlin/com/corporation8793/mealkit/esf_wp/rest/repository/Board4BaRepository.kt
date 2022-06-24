package com.corporation8793.mealkit.esf_wp.rest.repository

import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

class Board4BaRepository(val basicAuth : String) {
    /**
     * 게시물을 생성합니다.
     * @author  두동근
     * @param   title               제목
     * @param   content             내용
     * @param   categories          [RestClient]의 카테고리값 (기본값 : [RestClient.RECIPE_CUSTOMER])
     * * 고객 레시피 - [RestClient.RECIPE_CUSTOMER]
     * * 베스트 레시피 - [RestClient.RECIPE_BEST]
     * * 포인트 로그 - [RestClient.POINT_LOG]
     * @param   featured_media      [Media.id] (기본값 : 0)
     * @return  responseCode (expected : "201")
     */
    fun createPost(title : String, content : String, categories : String =
        RestClient.RECIPE_CUSTOMER, featured_media : String? = "0", excerpt: String) : Post? {

        val verifiedCategories = when (categories) {
            RestClient.RECIPE_CUSTOMER -> categories
            RestClient.RECIPE_BEST -> categories
            RestClient.POINT_LOG -> categories
            else -> {
                RestClient.RECIPE_CUSTOMER
            }
        }

        val call = RestClient.board4BaService.createPost(basicAuth, title = title, content = content,
            categories = verifiedCategories, featured_media = featured_media, excerpt = excerpt)

        return call.execute().body()
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
        val call = RestClient.board4BaService.retrieveOnePost(id = postId)

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
     * * 포인트 로그 - [RestClient.POINT_LOG]
     * @param   author              [RestClient.POINT_LOG] 사용자 지정 옵션
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     */
    fun retrievePostInCategories(per_page : String = "100", page : String = "1", order : String = "desc",
                                 categories : String = RestClient.RECIPE_CUSTOMER, author : String? = null) : Pair<String, List<Post>?> {
        val verifiedCategories = when (categories) {
            RestClient.RECIPE_CUSTOMER -> categories
            RestClient.RECIPE_BEST -> categories
            RestClient.POINT_LOG -> categories
            else -> {
                RestClient.RECIPE_CUSTOMER
            }
        }

        val call = RestClient.board4BaService.retrievePostInCategories(per_page, page, order, verifiedCategories, author)

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
        val call = RestClient.board4BaService.updatePost(basicAuth, id = postId, title = title, content = content)

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
        val call = RestClient.board4BaService.deletePost(basicAuth, id = postId)

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

        val response = RestClient.board4BaService.uploadMedia(basicAuth, filePart).execute()

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
        val response = RestClient.board4BaService.retrieveMedia(mediaId).execute()

        return Pair(response.code().toString(), response.body())
    }

    // Acf
    /**
     * 레시피 메타 수정
     * @author  두동근
     */
    fun updatePostAcf(id : String?, product : String?) : Pair<String, PostAcf?> {
        val response = RestClient.board4BaService.updatePostAcf(basicAuth, id, product).execute()

        return Pair(response.code().toString(), response.body())
    }

    // Point
    /**
     * 포인트 로그를 생성합니다.
     * @author  두동근
     * @param   title               제목
     * @param   content             내용
     * @param   categories          [RestClient]의 카테고리값 (기본값 : [RestClient.POINT_LOG])
     * * 포인트 로그 - [RestClient.POINT_LOG]
     * @param   featured_media      [Media.id] (기본값 : 0)
     * @return  responseCode (expected : "201")
     */
    fun createPointLog(title : String, content : String, categories : String =
        RestClient.POINT_LOG, featured_media : String? = "0", excerpt: String) : PointLog? {

        val verifiedCategories = when (categories) {
            RestClient.RECIPE_CUSTOMER -> categories
            RestClient.RECIPE_BEST -> categories
            RestClient.POINT_LOG -> categories
            else -> {
                RestClient.RECIPE_CUSTOMER
            }
        }

        val call = RestClient.board4BaService.createPointLog(basicAuth, title = title, content = content,
            categories = verifiedCategories, featured_media = featured_media, excerpt = excerpt)

        return call.execute().body()
    }

    /**
     * 카테고리 내 전체 게시물을 검색합니다.
     * @author  두동근
     * @param   per_page            페이지 당 게시물 개수 (기본값 : 100)
     * @param   page                선택한 페이지 (기본값 : 1)
     * @param   order               [Post.date]기준 내림차순(desc), 오름차순(asc) (기본값 : desc)
     * @param   categories          [RestClient]의 카테고리값 (기본값 : [RestClient.POINT_LOG])
     * * 포인트 로그 - [RestClient.POINT_LOG]
     * @param   author              [RestClient.POINT_LOG] 사용자 지정 옵션
     * @return  responseCode (expected : "200"), [List<Post>]
     * @see     Post
     * @see     Pair
     */
    fun retrievePointLog(per_page : String = "100", page : String = "1", order : String = "desc",
                                 categories : String = RestClient.POINT_LOG, author : String) : Pair<String, List<PointLog>?> {

        val response = RestClient.board4BaService.retrievePointLog(per_page, page, order,
            categories = categories,
            author = author
        ).execute()

        return Pair(response.code().toString(), response.body())
    }
}