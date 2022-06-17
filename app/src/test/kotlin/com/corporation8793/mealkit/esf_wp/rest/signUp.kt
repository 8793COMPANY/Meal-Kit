package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import org.junit.Assert
import org.junit.Test

class SignUp {
    @Test
    fun signUp() {
        val nonceRepository = NonceRepository()

        println("====== SignUp     ======")
        println("------ getNonce() ------")

        nonceRepository.getNonce()
        Assert.assertNotEquals("", nonceRepository.nonce)
        println("nonce value : ${nonceRepository.nonce}")

//        println("------ runSignUp() ------")
//
//        val runSignUpResponse = nonceRepository.runSignUp(
//            "test11@gmail.com",
//            "test11",
//            "testjohn",
//            "테스트(춘식이)",
//            SignUpBody(
//                Billing("광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
//                Shipping("광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
//                arrayOf(Meta_data(id = null, key = "recommender", value = "test1"))
//            )
//        )
//        Assert.assertEquals("201", runSignUpResponse.first)
//
//        println("runSignUp : ${runSignUpResponse.first}, ${runSignUpResponse.second}")


        println("--- checkUsername() ----")
        val checkUsernameResponse = nonceRepository.checkUsername("test11")
        println("checkUsername : ${checkUsernameResponse.first}, ${checkUsernameResponse.second?.get(0)?.username}")


        println("---- findUsername() ----")
        val findUsernameResponse = nonceRepository.findUsername("test11@gmail.com")
        println("findUsername : ${findUsernameResponse.first}, ${findUsernameResponse.second?.get(0)?.username}")


        println("-- sendPassResetLink() -")
        val sendPassResetLinkResponse = nonceRepository.sendPassResetLink("test11")
        println("sendPassResetLink : ${sendPassResetLinkResponse.first}, ${sendPassResetLinkResponse.second?.status}")





        println("====== EndTest    ======")
    }
}