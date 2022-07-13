package com.corporation8793.itsofresh.esf_wp.rest.data.sign_up

/**
 * ValidUserStatus data class
 * @author  두동근
 * @param   code            에러 코드
 * @param   message         에러 메시지
 */
data class ValidUserStatus(val status : String = "",
                           val cookie : String?,
                           val cookie_admin : String?,
                           val user : ValidUid?)
/**
 * ValidUid data class
 * @author  두동근
 * @param   id            인증된 사용자 uid
 */
data class ValidUid(val id : String?)