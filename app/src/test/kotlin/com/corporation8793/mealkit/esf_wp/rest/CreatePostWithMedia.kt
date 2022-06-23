package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.repository.Board4BaRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test
import java.io.File

class CreatePostWithMedia {
    @Test
    fun media() {
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        val board4BaRepository = Board4BaRepository(basicAuth)
        //var htmlContent = ""

        println("====== CreatePostWithMedia ======")



        println("------ 1. Upload              ------")
        // test image (test_img.jpeg)
        val file = File("src/test/kotlin/com/corporation8793/mealkit/esf_wp/rest/test_img.jpeg")
        val responseMedia = board4BaRepository.uploadMedia(file)
        //Assert.assertEquals("201", responseMedia.first)
        println("response Media URL : ${responseMedia.second?.guid?.rendered}")
        println("response Media ID : ${responseMedia.second?.id}")
        //htmlContent += "<p><img src=\"${responseMedia.second?.guid?.rendered}\"></p>"

        println("------ 2. Create              ------")
        var responseCode = board4BaRepository.createPost(
            featured_media = responseMedia.second?.id,
            title = "고든 RAM.J 가 전수한 유기농두부샐러드 레시피!!",
            excerpt = "유기농두부샐러드",
            content = "레시피를 순서대로 작성하여 주세요. J는 JORDY 의 J 입니다!!",
            )
        //Assert.assertEquals("201", responseCode)
        println("response Code : $responseCode\n")


        var responseCode2 = board4BaRepository.retrievePostInCategories(RestClient.RECIPE_CUSTOMER)

        //Assert.assertEquals("201", responseCode)
        println("response Code : ${responseCode2.second}\n")



//        println("------ Retrieve            ------")
//        var retrieveMedia = boardRepository.retrieveMedia(responseMedia.second?.id)
//        Assert.assertEquals("200", retrieveMedia.first)
//        println("retrieve Media URL : ${retrieveMedia.second?.guid?.rendered}")
//        println("retrieve Media ID : ${retrieveMedia.second?.id}")
//        println("retrieve Status : ${retrieveMedia.first}\n")




        println("====== EndTest             ======")
    }
}