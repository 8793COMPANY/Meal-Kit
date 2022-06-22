package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Assert
import org.junit.Test
import java.io.File

class CreatePostWithMedia {
    @Test
    fun media() {
        val testId = "john22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)
        //var htmlContent = ""


        println("====== CreatePostWithMedia ======")



        println("------ Upload              ------")
        // test image (test_img.jpeg)
        val file = File("src/test/kotlin/com/corporation8793/mealkit/esf_wp/rest/test_img.jpeg")
        val responseMedia = boardRepository.uploadMedia(file)
        Assert.assertEquals("201", responseMedia.first)
        println("response Media URL : ${responseMedia.second?.guid?.rendered}")
        println("response Media ID : ${responseMedia.second?.id}")
        //htmlContent += "<p><img src=\"${responseMedia.second?.guid?.rendered}\"></p>"



        println("------ Create              ------")
        var responseCode = boardRepository.createPost(
            title = "고든 RAM.J 가 전수한 유기농두부샐러드 레시피",
            content = "레시피를 순서대로 작성하여 주세요. J는 JORDY 의 J 입니다.",
            featured_media = responseMedia.second?.id
        )
        Assert.assertEquals("201", responseCode)
        println("response Code : $responseCode\n")



        println("------ Retrieve            ------")
        var retrieveMedia = boardRepository.retrieveMedia(responseMedia.second?.id)
        Assert.assertEquals("200", retrieveMedia.first)
        println("retrieve Media URL : ${retrieveMedia.second?.guid?.rendered}")
        println("retrieve Media ID : ${retrieveMedia.second?.id}")
        println("retrieve Status : ${retrieveMedia.first}\n")




        println("====== EndTest             ======")
    }
}