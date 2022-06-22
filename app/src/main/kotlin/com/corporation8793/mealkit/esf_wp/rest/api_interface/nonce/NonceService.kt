package com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce

import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.PassResetLink
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.ValidUserStatus
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Nonce, 회원가입의 인터페이스
 * @author  두동근
 */
interface NonceService {
    /**
     * [nonce]를 초기화합니다.
     * @author  두동근
     */
    @POST("api/get_nonce/?controller=user&method=generate_auth_cookie")
    fun getNonce() : Call<Nonce>

    /**
     * 유저를 검증합니다.
     * @author  두동근
     * @see     Pair
     */
    @GET("api/user/generate_auth_cookie/?insecure=cool")
    fun validationUser(@Query("username") username: String,
                       @Query("password") password: String,) : Call<ValidUserStatus>

    /**
     * 검증된 유저의 정보를 조회합니다.
     * @author  두동근
     */
    @GET("wp-json/wc/v3/customers/{id}")
    fun getValidUserInfo(@Path("id") id: String?) : Call<Customer>

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