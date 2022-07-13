package com.corporation8793.itsofresh.esf_wp.rest

import com.corporation8793.itsofresh.esf_wp.rest.repository.NonceRepository
import org.junit.Assert

class Login {

    fun Login () {
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

    }



}