package com.corporation8793.mealkit.esf_wp.rest.repository

import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.PassResetLink
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody

/**
 * [NonceService]의 구현 클래스
 * @author  두동근
 * @see     NonceService
 * @see     <a href="https://wordpress.org/plugins/json-api-user/">WP-Plugin [JSON API User]</a>
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
     * @see     <a href="https://wordpress.org/plugins/json-api-user/#method%3A%20register">Method: register [JSON API User]</a>
     */
    fun runSignUp(email: String,
                  username: String,
                  password: String,
                  first_name: String,
                  signUpBody: SignUpBody
    ) : Pair<String, Customer?> {
        val response = RestClient.nonceService.runSignUp(email, username, password, first_name, signUpBody).execute()

        return Pair(response.code().toString(), response.body())
    }
}