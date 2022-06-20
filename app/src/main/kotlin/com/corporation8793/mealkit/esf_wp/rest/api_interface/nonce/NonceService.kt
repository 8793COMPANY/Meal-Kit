package com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce

import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.PassResetLink
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Nonce, 회원가입의 인터페이스
 * @author  두동근
 * @see     <a href="https://wordpress.org/plugins/json-api-user/">WP-Plugin [JSON API User]</a>
 */
interface NonceService {
    /**
     * [nonce]를 초기화합니다.
     * @author  두동근
     */
    @POST("api/get_nonce/?controller=user&method=generate_auth_cookie")
    fun getNonce() : Call<Nonce>

    /**
     * 아이디를 조회합니다.
     * * "아이디 중복확인"에 사용할 수 있음.
     * * "추천인 아이디 확인"에 사용할 수 있음.
     * @author  두동근
     */
    @GET("wp-json/wc/v3/customers")
    fun checkUsername(@Query("search") search: String) : Call<Array<Customer>?>

    /**
     * 아이디를 조회합니다.
     * * "아이디 찾기"에 사용할 수 있음.
     * @author  두동근
     */
    @GET("wp-json/wc/v3/customers")
    fun findUsername(@Query("email") email: String) : Call<Array<Customer>?>

    /**
     * 비밀번호 초기화 링크를 이메일로 전송합니다.
     * * "비밀번호 찾기"에 사용할 수 있음.
     * * "비밀번호 변경"에 사용할 수 있음.
     * @author  두동근
     */
    @GET("api/user/retrieve_password/?insecure=cool")
    fun sendPassResetLink(@Query("user_login") user_login: String) : Call<PassResetLink>


    /**
     * 입력받은 정보로 회원가입합니다.
     * @author  두동근
     */
    @POST("wp-json/wc/v3/customers")
    fun runSignUp(@Query("email") email: String,
                  @Query("username") username: String,
                  @Query("password") password: String,
                  @Query("first_name") first_name: String,
                  @Body signUpBody: SignUpBody
    ) : Call<Customer>
}