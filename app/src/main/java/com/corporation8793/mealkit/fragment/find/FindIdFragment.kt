package com.corporation8793.mealkit.fragment.find

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.BestAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import com.corporation8793.mealkit.fragment.HomeFragmentDirections
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
class FindIdFragment() : Fragment() {
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
        var view = inflater.inflate(R.layout.fragment_find_id, container, false)

        val find_id_btn = view.findViewById<Button>(R.id.find_id_btn)
        val verification_code_input_box = view.findViewById<EditText>(R.id.verification_code_input_box)

        verification_code_input_box.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.trim().length > 5) {
//                    val drawable = find_id_btn.background as GradientDrawable
                    find_id_btn.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.app_basic_color)
                    find_id_btn.isEnabled = true
//                    drawable.setColor(ContextCompat.getColor(context!!,R.color.app_basic_color))
                }else{
//                    val drawable = find_id_btn.background as GradientDrawable
//                    drawable.setColor(ContextCompat.getColor(context!!,R.color.gray_dddddd))
                    find_id_btn.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.gray_dddddd)
                    find_id_btn.isEnabled = false
                }
            }

        })

        find_id_btn.setOnClickListener{
            if (!verification_code_input_box.text.toString().trim().equals("")) {
                GlobalScope.launch(Dispatchers.Default) {
                    val value = NonceRepository().findUsername(verification_code_input_box.text.toString().trim())
                    println(value)
                    println(value.first)

                    val check = value.first
                    var id = ""

                    if(check.equals("200"))
                        id = value.second?.get(0)?.username!!

                    GlobalScope.launch(Dispatchers.Main) {
                        if (check.equals("200")){
                            val bundle = bundleOf("email" to id)

                            Navigation.findNavController(view).navigate(R.id.action_find_id_screen, bundle)

                        }else{
                           Toast.makeText(context,"아이디를 찾지 못했습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(context,"이메일을 입력해주세요.",Toast.LENGTH_SHORT).show()
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
                FindIdFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}