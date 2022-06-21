package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import okhttp3.Credentials
import org.junit.Test

class productOrder {
    @Test
    fun productOrder() {
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)

        println("====== productOrder    ======")

        println("------ productOrder()  -----")
        //val Response = boardRepository.()
        //println("productOrder : ${.first}, ${.second}")


        println("====== EndTest         ======")
    }
}