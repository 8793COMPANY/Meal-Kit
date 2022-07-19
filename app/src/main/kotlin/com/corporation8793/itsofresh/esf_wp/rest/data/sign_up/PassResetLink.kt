package com.corporation8793.itsofresh.esf_wp.rest.data.sign_up

/**
 * PassResetLink data class
 * @author  두동근
 * @param   status          에러 코드 (expected : "ok")
 * @param   msg             에러 메시지
 */
data class PassResetLink(val status : String = "", val msg : String = "")
