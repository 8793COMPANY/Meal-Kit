package com.corporation8793.itsofresh.esf_wp.rest

import com.corporation8793.itsofresh.esf_wp.rest.repository.Board4BaRepository
import com.corporation8793.itsofresh.esf_wp.rest.repository.NonceRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test

class CreatePostWithMedia {
    @Test
    fun media() {
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        val board4BaRepository = Board4BaRepository(basicAuth)
        val nonceRepository = NonceRepository()
        //var htmlContent = ""

        println("====== CreatePostWithMedia ======")



//        println("------ 1. Upload              ------")
//        // test image (test_img.jpeg)
//        val file = File("src/test/kotlin/com/corporation8793/mealkit/esf_wp/rest/test_img.jpeg")
//        val responseMedia = board4BaRepository.uploadMedia(file)
//
//        println("response Media URL : ${responseMedia.second?.guid?.rendered}")
//        println("response Media ID : ${responseMedia.second?.id}")
//        //htmlContent += "<p><img src=\"${responseMedia.second?.guid?.rendered}\"></p>"

//        println("------ 2. Create              ------")
//        val responseCode = board4BaRepository.createPost(
//            featured_media = responseMedia.second?.id,
//            title = "고든 RAM.J 가 전수한 유기농두부샐러드 레시피!!?!",
//            excerpt = "레시피 요약 부분 입니다.",
//            content = "레시피를 순서대로 작성하여 주세요. J는 JORDY 의 J 입니다!!?!",
//            )
//        println("------ 2-1. Add meta          ------")
//        val addMeta = board4BaRepository.updatePostAcf(
//            responseCode?.id,
//            "유기농두부샐러드"
//        )
//
//        println("response Code : $responseCode\n")


        println("------ 3. Retrieve (리스트 불러오기)            ------")
        // 고객 레시피 카테고리는 featured_media_src_url, title, excerpt 를 뿌려줍니다.
        // 베스트 레시피 카테고리는 featured_media_src_url, title, price 를 뿌려줍니다.

        // 카테고리 지정 필요 -> RestClient.RECIPE_CUSTOMER, RestClient.RECIPE_BEST
        val responseCode2 = board4BaRepository.retrievePostInCategories(categories = RestClient.RECIPE_CUSTOMER)
        //val responseCode2 = board4BaRepository.retrievePostInCategories(categories = RestClient.RECIPE_BEST)

        println("response Code : ${responseCode2.second}\n")


//        println("------ 4. Find Author Image   ------")
//        // 3번 리스트 불러오기에서 조그만 글쓴이 프로필 사진 가져올때 사용합니다.
//
//        val authorData = nonceRepository.getValidUserInfo(responseCode2.second?.first()?.author)
//        //val filteredData = authorData.second?.meta_data?.filter { metaData -> metaData.key == "profile_img" }
//        //    ?.first()?.value.toString()
//
//        //var authorImage = board4BaRepository.retrieveMedia(filteredData?.first()?.value.toString())
//        var authorImage = authorData.second?.meta_data?.filter { metaData -> metaData.key == "profile_img" }
//            ?.first()?.value.toString()
//
//        println("Author Profile Image URL : ${authorImage}\n")


//        println("------ 5. Retrieve One (단일 게시물 보기)   ------")
//        // 3번 리스트 불러오기에서 클릭한 인덱스로 데이터 가져오시면 됩니다. 아래는 예시
//        // 별도로 단일 게시물 데이터 통신이 필요하시면 'retrieveOnePost' 를 사용해주세요.
//        println("레시피 사진 : ${responseCode2.second?.get(0)?.featured_media_src_url}")
//        println("레시피 제목 : ${responseCode2.second?.get(0)?.title?.rendered}")
//        println("사용 밀키트 : ${responseCode2.second?.get(0)?.acf?.product}")
//        println("레시피 요약 : ${responseCode2.second?.get(0)?.excerpt?.rendered}")
//        println("레시피 내용 : ${responseCode2.second?.get(0)?.content?.rendered}")



//        println("------ Retrieve            ------")
//        var retrieveMedia = boardRepository.retrieveMedia(responseMedia.second?.id)
//        Assert.assertEquals("200", retrieveMedia.first)
//        println("retrieve Media URL : ${retrieveMedia.second?.guid?.rendered}")
//        println("retrieve Media ID : ${retrieveMedia.second?.id}")
//        println("retrieve Status : ${retrieveMedia.first}\n")



        println("------ 6. Update One (신고하기)             ------")
        var declarePost = board4BaRepository.updatePost("2042")
        Assert.assertEquals("200", declarePost)
        println("declarePost : $declarePost")




        println("\n====== EndTest             ======")
    }



}