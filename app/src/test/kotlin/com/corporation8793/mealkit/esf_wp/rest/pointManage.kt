package com.corporation8793.mealkit.esf_wp.rest

import com.corporation8793.mealkit.esf_wp.rest.repository.Board4BaRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import okhttp3.Credentials
import org.junit.Test

class pointManage {
    @Test
    fun pointManage() {
        val testId = "test22"
        val testPw = "1234"
        val basicAuth = Credentials.basic(testId, testPw)
        val board4BaRepository = Board4BaRepository(basicAuth)
        val nonceRepository = NonceRepository()

        println("====== pointManage     ======")



        println("====== EndTest         ======")
    }
}