package com.corporation8793.itsofresh.esf_wp.rest.repository

import com.corporation8793.itsofresh.esf_wp.rest.RestClient
import com.corporation8793.itsofresh.esf_wp.rest.data.*
import com.corporation8793.itsofresh.esf_wp.rest.data.sign_up.PassResetLink
import com.corporation8793.itsofresh.esf_wp.rest.data.sign_up.SignUpBody
import com.corporation8793.itsofresh.esf_wp.rest.data.sign_up.ValidUserStatus

/**
 * [NonceService]의 구현 클래스
 * @author  두동근
 * @see     NonceService
 */
class NonceRepository {
    /**
     * WP용 암호화 임시값(Nonce)
     */
    var nonce = ""

    /**
     * [nonce]를 초기화합니다.
     * @author  두동근
     */
    fun getNonce() {
        val call = RestClient.nonceService.getNonce()

        // for test (execute)
        nonce = call.execute().body()!!.nonce
    }
    /**
     * 유저를 검증합니다.
     * @author  두동근
     * @return  responseCode (expected : "200"), [ValidUserStatus]
     * @param   username        회원의 로그인 아이디
     * @param   password        회원의 로그인 패스워드
     * @see     Pair
     * @see     ValidUserStatus
     */
    fun validationUser(username: String, password: String) : Pair<String, ValidUserStatus?> {
        val response = RestClient.nonceService.validationUser(username, password).execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 검증된 유저의 정보를 조회합니다.
     * @author  두동근
     * @param   id        [validationUser] id
     * @return  responseCode (expected : "200"), [Customer]
     * @see     Pair
     */
    fun getValidUserInfo(id: String?) : Pair<String, Customer?> {
        val response = RestClient.nonceService.getValidUserInfo(id).execute()

        return Pair(response.code().toString(), response.body())
    }
    fun Login(username: String, password: String) : Pair<String?, Customer?> {
        val validationUserResponse = RestClient.nonceService.validationUser(username, password).execute().body()
        var response : Customer? = null

        if (validationUserResponse != null) {
            when (validationUserResponse.status) {
                "error" -> {
                    println("validation : error")
                }
                "ok" -> {
                    println("validationUser id(${validationUserResponse.user?.id}) → login ok")
                    println("------------------------")
                    val getValidUserInfoResponse = getValidUserInfo(validationUserResponse.user?.id)
                    println("Info : ${validationUserResponse.status}, ${getValidUserInfoResponse.second}")
                    println("------------------------")
                    response = getValidUserInfoResponse.second!!
                }
            }
        }

        return Pair(validationUserResponse?.status, response)
    }
    /**
     * 아이디를 조회합니다.
     * * "아이디 중복확인"에 사용할 수 있음.
     * * "추천인 아이디 확인"에 사용할 수 있음.
     * @author  두동근
     * @param   search        회원의 로그인 아이디
     */
    fun checkUsername(search: String) : Pair<String, Array<Customer>?> {
        val response = RestClient.nonceService.checkUsername(search).execute()

        return Pair(response.code().toString(), response.body())
    }
    /**
     * 아이디를 조회합니다.
     * * "아이디 찾기"에 사용할 수 있음.
     * @author  두동근
     * @param   email           회원의 이메일
     */
    fun findUsername(email: String) : Pair<String, Array<Customer>?> {
        val response = RestClient.nonceService.findUsername(email).execute()

        return Pair(response.code().toString(), response.body())
    }

    /**
     * 비밀번호 초기화 링크를 이메일로 전송합니다.
     * * "비밀번호 찾기"에 사용할 수 있음.
     * * "비밀번호 변경"에 사용할 수 있음.
     * @author  두동근
     * @param   user_login        회원의 로그인 아이디
     */
    fun sendPassResetLink(user_login: String) : Pair<String, PassResetLink?> {
        val response = RestClient.nonceService.sendPassResetLink(user_login).execute()

        return Pair(response.code().toString(), response.body())
    }

    /**
     * 입력받은 정보로 회원가입합니다.
     * @author  두동근
     * @param   email           회원의 이메일
     * @param   username        회원의 로그인 아이디
     * @param   password        회원의 로그인 패스워드
     * @param   first_name      고객의 이름
     * @param   signUpBody      [SignUpBody] 클래스
     */
    fun runSignUp(email: String,
                  username: String,
                  password: String,
                  first_name: String,
                  signUpBody: SignUpBody
    ) : Pair<String, Customer?> {
        val validResponseUsername = RestClient.nonceService.checkUsername(username).execute()
        val validResponseEmail = RestClient.nonceService.findUsername(email).execute()
        val vru = Triple(validResponseUsername.code(), validResponseUsername.message(), validResponseUsername.body())
        val vre = Triple(validResponseEmail.code(), validResponseEmail.message(), validResponseEmail.body())

        return when {
                // 정상동작
            ((vru.first == 200 && vru.third?.size == 0) && (vre.first == 200 && vre.third?.size == 0)) -> {
                val response = RestClient.nonceService.runSignUp(email, username, password, first_name, signUpBody).execute()
                Pair(response.code().toString(), response.body())
            }
            ((vru.first == 200 && vru.third?.size == 0) && (vre.first == 200 && vre.third?.size != 0)) -> {
                // 중복 이메일
                Pair("501", null)
            }
            ((vru.first == 200 && vru.third?.size != 0) && (vre.first == 200 && vre.third?.size == 0)) -> {
                // 중복 아이디
                Pair("502", null)
            }
            ( (vre.first == 400 && vre.third?.size == null)) -> {
                // 중복 아이디, 올바르지 않은 이메일 형식
                Pair("504", null)
            }
            else -> {
                // 예외발생
                println("아이디 로그 : ${vru.first} / ${vru.second} / ${vru.third?.size}")
                println("이메일 로그 : ${vre.first} / ${vre.second} / ${vre.third?.size}")
                Pair("510", null)
            }
        }
    }

    fun updateCustomer(id: String, first_name: String, signUpBody: SignUpBody) : Pair<String, Customer?> {
        val response = RestClient.nonceService.updateCustomer(id = id ,first_name = first_name, signUpBody = signUpBody).execute()
        return Pair(response.code().toString(), response.body())
    }

    // 포인트 제어 (+, -)
    // 14번 유저의 100 포인트를 증가시킨다. (사유 : 샐러드가게) -> 14, 100, +, 샐러드가게
    // 14번 유저의 500 포인트를 차감한다. (사유 : 스프가게) -> 14, 500, -, 스프가게
    fun editPoint(b4ba : Board4BaRepository, id: String, point: Int, action: String, log: String) : Pair<String, Customer?> {
        // title, excerpt, logContent
        var logContent = ""
        var logResult : Triple<String, String, String>

        val validUserResponse = RestClient.nonceService.getValidUserInfo(id).execute()
        val currentUserTotalPoint = validUserResponse.body()?.meta_data?.filter {
                metaData -> metaData.key == "point" }?.first()?.value.toString().toInt()
        println("Before 포인트 : $currentUserTotalPoint")
        logContent += "Before 포인트 : $currentUserTotalPoint\n"

        return when (action) {
            "+" -> {
                val editPointResponse = RestClient.nonceService.editPoint(id, editPointBody(
                        arrayOf(
                            Meta_data(id = null, key = "point",
                                value = (currentUserTotalPoint + point) )
                        )
                    )
                ).execute()
                println("After 포인트 : ${editPointResponse.body()?.meta_data?.filter { 
                        metaData -> metaData.key == "point" }?.first()?.value}")
                logContent += "After 포인트 : ${editPointResponse.body()?.meta_data?.filter {
                        metaData -> metaData.key == "point" }?.first()?.value}\n"
                logResult = Triple(log, "$action $point", logContent)
                val logResponse = b4ba.createPointLog(
                    title = logResult.first,
                    excerpt = logResult.second,
                    content = logResult.third,
                    categories = RestClient.POINT_LOG
                )

                Pair(editPointResponse.code().toString(), editPointResponse.body())
            }
            "-" -> {
                val editPointResponse = RestClient.nonceService.editPoint(id, editPointBody(
                        arrayOf(
                            Meta_data(id = null, key = "point",
                                value = (currentUserTotalPoint - point) )
                        )
                    )
                ).execute()
                println("After 포인트 : ${editPointResponse.body()?.meta_data?.filter {
                        metaData -> metaData.key == "point" }?.first()?.value}")
                logContent += "After 포인트 : ${editPointResponse.body()?.meta_data?.filter {
                        metaData -> metaData.key == "point" }?.first()?.value}\n"
                logResult = Triple(log, "$action $point", logContent)
                val logResponse = b4ba.createPointLog(
                    title = logResult.first,
                    excerpt = logResult.second,
                    content = logResult.third,
                    categories = RestClient.POINT_LOG
                )

                Pair(editPointResponse.code().toString(), editPointResponse.body())
            }
            "recommender" -> {
                val editPointResponse = RestClient.nonceService.editPoint(id, editPointBody(
                    arrayOf(
                        Meta_data(id = null, key = "point",
                            value = (currentUserTotalPoint + point) )
                    )
                )
                ).execute()
                println("After 포인트 : ${editPointResponse.body()?.meta_data?.filter {
                        metaData -> metaData.key == "point" }?.first()?.value}")
                logContent += "After 포인트 : ${editPointResponse.body()?.meta_data?.filter {
                        metaData -> metaData.key == "point" }?.first()?.value}\n"
                logResult = Triple(log, "+ $point", logContent)
                val logResponse = b4ba.createPointLogByOther(
                    title = logResult.first,
                    excerpt = logResult.second,
                    content = logResult.third,
                    categories = RestClient.POINT_LOG,
                    author = id
                )

                Pair(editPointResponse.code().toString(), editPointResponse.body())
            }
            else -> {
                Pair("null", null)
            }
        }
    }
}