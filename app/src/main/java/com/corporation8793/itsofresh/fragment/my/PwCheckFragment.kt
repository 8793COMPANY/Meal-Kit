package com.corporation8793.itsofresh.fragment.my

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.activity.UserInfoActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PwCheckFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var next_btn : Button
    lateinit var pw_check_input_box : EditText
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
        var view = inflater.inflate(R.layout.fragment_pw_check, container, false)

        pw_check_input_box = view.findViewById<EditText>(R.id.pw_check_input_box)
        next_btn = view.findViewById<Button>(R.id.next_btn)

        pw_check_input_box.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.trim().length > 5) {
                    btnColorChange(R.color.app_basic_color)
                    next_btn.setTextColor(resources.getColor(R.color.app_basic_color))
                }else{
                    btnColorChange(R.color.gray_dddddd)
                    next_btn.setTextColor(resources.getColor(R.color.text_inactivation_color))
                }
            }

        })

        next_btn.setOnClickListener{
            (activity as UserInfoActivity).changeFragment(2)
        }

        btnColorChange(R.color.gray_dddddd)

        return view
    }

    fun btnColorChange(color: Int){
        val drawable = next_btn.background as GradientDrawable
        drawable.setStroke(3, ContextCompat.getColor(context!!,color))

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
                PwCheckFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}