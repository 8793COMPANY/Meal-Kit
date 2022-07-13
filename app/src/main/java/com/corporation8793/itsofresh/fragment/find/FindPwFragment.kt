package com.corporation8793.itsofresh.fragment.find

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.activity.ChangePwActivity
import com.corporation8793.itsofresh.dto.BestItem
import com.corporation8793.itsofresh.esf_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FindPwFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<BestItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_find_pw, container, false)

        val id_input_box = view.findViewById<EditText>(R.id.id_input_box)
        val find_pw_btn = view.findViewById<Button>(R.id.find_pw_btn)


        id_input_box.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.trim().length > 5) {
//                    val drawable = find_id_btn.background as GradientDrawable
                    find_pw_btn.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.app_basic_color)
                    find_pw_btn.isEnabled = true
//                    drawable.setColor(ContextCompat.getColor(context!!,R.color.app_basic_color))
                }else{
//                    val drawable = find_id_btn.background as GradientDrawable
//                    drawable.setColor(ContextCompat.getColor(context!!,R.color.gray_dddddd))
                    find_pw_btn.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.gray_dddddd)
                    find_pw_btn.isEnabled = false
                }
            }

        })

        find_pw_btn.setOnClickListener{
            GlobalScope.launch(Dispatchers.Default) {
                val value = NonceRepository().sendPassResetLink(id_input_box.text.toString().trim())
                println("value : "+value)
                println("value first: "+value.first)
                println("value second: "+value.second)

                val status = value.second?.status
                var email = ""

                if (status.equals("ok")) {
                    val user_email = NonceRepository().checkUsername(id_input_box.text.toString().trim())
                    Log.e("user_email",user_email.second?.get(0).toString())
                    Log.e("user_email",user_email.first)
                    email = user_email.second?.get(0)?.email!!
                }

                GlobalScope.launch(Dispatchers.Main) {
                    if (!status.equals("error")){
                        requireActivity().finish()
                        var intent = Intent(activity, ChangePwActivity::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)

                    }else{
                        Toast.makeText(context,"아이디를 찾지 못했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }





        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FindPwFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}