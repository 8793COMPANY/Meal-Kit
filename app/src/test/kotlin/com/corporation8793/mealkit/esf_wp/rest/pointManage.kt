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

        println("====== pointManage            ======")
        println("------ 1. 총 보유 포인트      ------")
        val validUserResponse = nonceRepository.getValidUserInfo("14")
        val currentUserTotalPoint = validUserResponse.second?.meta_data?.filter {
                metaData -> metaData.key == "point" }?.first()?.value.toString().toInt()
        println("총 보유 포인트 : $currentUserTotalPoint\n")

        println("------ 2. 포인트 제어         ------")
        println("------ 2-1. 포인트 증가       ------")
        val editPlusPointResponse = nonceRepository.editPoint(
            b4ba = board4BaRepository,
            id = "14",
            point = 100,
            action = "+",
            log = "왕맛있는 샐러드가게"
        )

        println("------ 2-2. 포인트 감소       ------")
        val editMinusPointResponse = nonceRepository.editPoint(
            b4ba = board4BaRepository,
            id = "14",
            point = 50,
            action = "-",
            log = "유니세프 기부"
        )

        println("\n------ 3. 포인트 내역         ------")
        val retrievePointLogResponse = board4BaRepository.retrievePointLog(
            author = "14",
            categories = RestClient.POINT_LOG
        )

        println("총 ${retrievePointLogResponse.second?.size}건")
        println("------------------------------------")
        for ((i, post) in retrievePointLogResponse.second?.withIndex()!!) {
            println("* ${(i+1)}번째 내역")
            println("사용처 : ${post.title.rendered} || ${post.excerpt.rendered} / 사용일시 : ${post.date}")
        }

        println("====== EndTest                ======")
    }
}