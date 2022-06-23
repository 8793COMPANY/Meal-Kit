package com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce

import com.corporation8793.mealkit.esf_wp.rest.data.Media
import com.corporation8793.mealkit.esf_wp.rest.data.Post
import com.corporation8793.mealkit.esf_wp.rest.data.PostAcf
import okhttp3.Credentials
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * 게시판(레시피, 미디어 등)의 인터페이스
 * @author  두동근
 * @see     Credentials
 */
interface Board4BaService {
    /**
     * 게시물을 생성합니다.
     * @author  두동근
     */
    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts")
    fun createPost(@Header("Authorization") h1 : String,
                   @Field("status") status : String = "publish",
                   @Field("title") title : String,
                   @Field("excerpt") excerpt : String,
                   @Field("content") content : String,
                   @Field("categories") categories : String = "10",
                   @Field("featured_media") featured_media : String? = "0") : Call<ResponseBody>
    /**
     * id([postId])가 일치하는 게시물을 검색합니다.
     * * (id가 일치하는 게시물은 1개(One)입니다.)
     * @author  두동근
     * @see     Post
     */
    @GET("wp-json/wp/v2/posts/{id}")
    fun retrieveOnePost(@Path("id") id : String) : Call<Post>
    /**
     * 카테고리 내 전체 게시물을 검색합니다.
     * @author  두동근
     * @see     Post
     */
    @GET("wp-json/wp/v2/posts")
    fun retrievePostInCategories(@Query("per_page") per_page : String = "100",
                                 @Query("page") page : String = "1",
                                 @Query("order") order : String = "desc",
                                 @Query("categories") categories : String) : Call<List<Post>>
    /**
     * 게시물을 수정합니다.
     * * id([postId])가 일치하는 게시물을 수정합니다.
     * @author  두동근
     */
    @FormUrlEncoded
    @POST("wp-json/wp/v2/posts/{id}")
    fun updatePost(@Header("Authorization") h1 : String,
                   @Path("id") id : String,
                   @Field("status") status : String = "publish",
                   @Field("title") title : String,
                   @Field("content") content : String,
                   @Field("categories") categories : String = "10",
                   @Field("featured_media") featured_media : String = "0") : Call<ResponseBody>
    /**
     * 게시물을 삭제합니다.
     * * id([postId])가 일치하는 게시물을 삭제합니다.
     * @author  두동근
     */
    @DELETE("wp-json/wp/v2/posts/{id}?force=true")
    fun deletePost(@Header("Authorization") h1 : String,
                   @Path("id") id : String) : Call<ResponseBody>

    // Media
    /**
     * [Post.featured_media]로 사용할 이미지를 업로드합니다.
     * @author  두동근
     * @see     Media
     * @see     Pair
     * @see     File
     * @see     Post.featured_media
     */
    @Multipart
    @POST("wp-json/wp/v2/media")
    fun uploadMedia(@Header("Authorization") h1 : String,
                    @Part file : MultipartBody.Part) : Call<Media>
    /**
     * id([mediaId])가 일치하는 미디어를 검색합니다.
     * * (id가 일치하는 미디어는 1개(One)입니다.)
     * @author  두동근
     * @see     Media
     * @see     Pair
     * @see     Post.featured_media
     */
    @GET("wp-json/wp/v2/media/{id}")
    fun retrieveMedia(@Path("id") id : String?) : Call<Media>
}