package com.corporation8793.mealkit.esf_wp.rest.data

/**
 * 레시피 data class
 * @author  두동근
 * @param   id                          게시물의 고유 번호(Post ID)
 * @param   date                        게시물의 작성 일자
 * @param   author                      게시물을 작성한 회원
 * @param   categories                  게시물이 속한 카테고리
 * @param   featured_media              게시물의 대표 사진 리소스 ID값
 * @param   featured_media_src_url      게시물의 대표 사진 리소스 HTTP URL
 * @param   title                       [PostTitle] 클래스
 * @param   excerpt                     [PostExcerpt] 클래스
 * @param   content                     [PostContent] 클래스
 * @param   acf                         [Acf] 클래스
 */
data class Post(val id : String?,
                val date : String?,
                val author : String?,
                val categories : Array<String>,
                val featured_media : String,
                val featured_media_src_url : String,
                val title : PostTitle,
                val excerpt : PostExcerpt,
                val content : PostContent,
                val acf : PostAcf)
/**
 * [Post.title]
 * @author  두동근
 * @param   rendered    레시피 이름
 */
data class PostTitle(val rendered : String)
/**
 * [Post.excerpt]
 * @author  두동근
 * @param   rendered    사용 밀키트
 */
data class PostExcerpt(val rendered : String)
/**
 * [Post.content]
 * @author  두동근
 * @param   rendered    레시피 소개
 */
data class PostContent(val rendered : String)
/**
 * 게시물 ACF(Advanced Custom Fields)
 * @author  두동근
 * @see     [Post.acf]
 */
data class PostAcf(val product : String, val price : String, val product_likes : Any)