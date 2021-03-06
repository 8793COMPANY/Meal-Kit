package com.corporation8793.itsofresh.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.activity.AccessTermsActivity
import com.corporation8793.itsofresh.activity.UserEditActivity
import com.corporation8793.itsofresh.adapter.MyAdapter
import com.corporation8793.itsofresh.dto.MyItem
import com.corporation8793.itsofresh.fragment.my.PurchaseDetailsActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = ArrayList<MyItem>()
    lateinit var sharedPreference : SharedPreferences
    lateinit var user_name : TextView


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
        var view = inflater.inflate(R.layout.fragment_my, container, false)

        val my_list = view.findViewById<GridView>(R.id.my_list)
        user_name = view.findViewById<TextView>(R.id.user_name)
        val go_user_info_edit_btn = view.findViewById<TextView>(R.id.go_user_info_edit_btn)


        val personal_information_processing_policy = view.findViewById<TextView>(R.id.personal_information_processing_policy)
        val terms_and_conditions = view.findViewById<TextView>(R.id.terms_and_conditions)
        val refund_and_return_policy = view.findViewById<TextView>(R.id.refund_and_return_policy)

        user_name.setText(MainApplication.instance.user.first_name)

        go_user_info_edit_btn.setOnClickListener {
           val intent = Intent(activity,UserEditActivity::class.java)
            startActivity(intent);
        }

        sharedPreference = context!!.getSharedPreferences("other", 0)
        var auto_login_check = sharedPreference.getBoolean("autoLogin", false)



        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 6.5).toInt()

        datas.apply {
            datas.clear()
//            add(MyItem(R.drawable.my_event_icon,"?????????","N",false))
            add(MyItem(R.drawable.my_purchase_details_icon,"????????????","3",false))
//            add(MyItem(R.drawable.my_shop_list_icon,"?????? ?????? ??????","N",false))
            add(MyItem(R.drawable.my_point_icon,"?????????","N",false))
            add(MyItem(R.drawable.my_auto_login_btn_selector,"???????????????","",auto_login_check))
            add(MyItem(R.drawable.my_friend_icon,"????????????","N",false))
            add(MyItem(R.drawable.my_kakao_icon,"???????????? ??????","N",false))

        }

        var myAdapter = MyAdapter(context,datas,findNavController())

        my_list.adapter = myAdapter


        my_list.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Log.e("check",i.toString())
            if (i==0){
                var intent = Intent(context, PurchaseDetailsActivity::class.java)
                intent.putExtra("type","my")
                startActivity(intent)
            } else if (i == 1){
                val action = MyFragmentDirections.actionMyToPointFragment()
                findNavController().navigate(action)
            }else if (i == 2){
                var auto_login = view.findViewById<ImageView>(R.id.about_my_list_img)

                if (auto_login.isSelected){
                    auto_login.isSelected = false
                    auto_login_update(false)
                }else{
                    auto_login.isSelected = true
                    auto_login_update(true)
                }

            }

        })

        personal_information_processing_policy.setOnClickListener {
            var intent = Intent(context, AccessTermsActivity::class.java)
            intent.putExtra("type","????????????????????????")
            startActivity(intent)
        }

        terms_and_conditions.setOnClickListener {
            var intent = Intent(context, AccessTermsActivity::class.java)
            intent.putExtra("type","????????? ????????????")
            startActivity(intent)
        }


        refund_and_return_policy.setOnClickListener {
            var intent = Intent(context, AccessTermsActivity::class.java)
            intent.putExtra("type","?????? ??? ?????? ??????")
            startActivity(intent)
        }



        return view
    }

    fun auto_login_update(check : Boolean){
        val edit: SharedPreferences.Editor = sharedPreference.edit()
        edit.putBoolean("autoLogin", check)
        edit.commit()
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
                MyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onResume() {
        super.onResume()
        if (user_name != null)
            user_name.text = MainApplication.instance.user.first_name
    }
}