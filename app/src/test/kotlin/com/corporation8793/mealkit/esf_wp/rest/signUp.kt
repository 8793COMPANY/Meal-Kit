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

        println("\n------ runLogin() ------")
        val loginResponse = nonceRepository.Login(username = "test22", password = "153")
        println("runLogin : ${loginResponse.first}, ${loginResponse.second}")
        println("------------------------\n")


//        println("------ runSignUp() ------")
//
//        val runSignUpResponse = nonceRepository.runSignUp(
//            "gyeongyeongi22@gmail.com",
//            "test22",
//            "1234",
//            "테스트(경민)",
//            SignUpBody(
//                Billing("광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
//                Shipping("광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", "010-8793-8793"),
//                arrayOf(Meta_data(id = null, key = "recommender", value = "test2"))
//            )
//        )
//        Assert.assertEquals("201", runSignUpResponse.first)
//
//        println("runSignUp : ${runSignUpResponse.first}, ${runSignUpResponse.second}")

        println("--- checkUsername() ----")
        val checkUsernameResponse = nonceRepository.checkUsername("test22")
        println("checkUsername : ${checkUsernameResponse.first}, ${checkUsernameResponse.second?.get(0)}")


        println("---- findUsername() ----")
        val findUsernameResponse = nonceRepository.findUsername("gyeongyeongi22@gmail.com")
        println("findUsername : ${findUsernameResponse.first}, ${findUsernameResponse.second?.get(0)}")


        println("-- sendPassResetLink() -")
        val sendPassResetLinkResponse = nonceRepository.sendPassResetLink("test22")
        println("sendPassResetLink : ${sendPassResetLinkResponse.first}, ${sendPassResetLinkResponse.second?.status}")





        println("====== EndTest    ======")
    }
}