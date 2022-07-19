package com.corporation8793.itsofresh.esf_wp.rest.data.sign_up

import com.corporation8793.itsofresh.esf_wp.rest.data.Billing
import com.corporation8793.itsofresh.esf_wp.rest.data.Meta_data
import com.corporation8793.itsofresh.esf_wp.rest.data.Shipping

/**
 * SignUpBody data class
 * @author  두동근
 * @param   billing             [Billing] 클래스
 * @param   shipping            [Shipping] 클래스
 * @param   meta_data           [meta_data] 클래스
 */
data class SignUpBody(val billing : Billing,
                      val shipping : Shipping,
                      val meta_data : Array<Meta_data>)