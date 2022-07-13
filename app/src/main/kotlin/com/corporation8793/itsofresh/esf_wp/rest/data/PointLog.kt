package com.corporation8793.itsofresh.esf_wp.rest.data

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
 */
data class PointLog(val id : String?,
                    val date : String?,
                    val author : String?,
                    val categories : Array<String>,
                    val title : PointLogTitle,
                    val excerpt : PointLogExcerpt,
                    val content : PointLogContent)
/**
 * [PointLog.title]
 * @author  두동근
 * @param   rendered
 */
data class PointLogTitle(val rendered : String)
/**
 * [PointLog.excerpt]
 * @author  두동근
 * @param   rendered
 */
data class PointLogExcerpt(val rendered : String)
/**
 * [PointLog.content]
 * @author  두동근
 * @param   rendered
 */
data class PointLogContent(val rendered : String)