package com.corporation8793.mealkit.fragment

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.activity.FindActivity
import com.corporation8793.mealkit.adapter.BestAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.Product
import com.corporation8793.mealkit.fragment.my.PurchaseDetailsFragment
import com.corporation8793.mealkit.payment.PayMentActivity
import com.corporation8793.mealkit.payment.SelectProductActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KitDetailFragment() : Fragment() {
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
        var view = inflater.inflate(R.layout.fragment_kit_detail, container, false)
        val payment_btn = view.findViewById<ConstraintLayout>(R.id.payment_btn);
        val like_btn = view.findViewById<ImageView>(R.id.like_btn);
        var id = arguments?.getString("id")
        var category = arguments?.getString("category")
        var name = arguments?.getString("name")
        var price = arguments?.getString("price")
        var stock = arguments?.getString("stock")
        var img = arguments?.getString("img")
        var like = arguments?.getBoolean("like")


        Glide.with(this).load(img).into(view.findViewById<ImageView>(R.id.kit_img))
        view.findViewById<TextView>(R.id.kit_category).setText(category)
        view.findViewById<TextView>(R.id.kit_name).setText(name)
        view.findViewById<TextView>(R.id.price).setText(price)
        view.findViewById<TextView>(R.id.stock_count).setText(stock+"개")
        view.findViewById<ImageView>(R.id.kit_img)



        view.findViewById<Button>(R.id.back_btn).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_back_to_home)
        }

        like_btn.isSelected = like!!;


        like_btn.setOnClickListener {
            if (like_btn.isSelected){


                GlobalScope.launch(Dispatchers.Default) {
                    val resultOn = MainApplication.instance.boardRepository.productLikesEdit(id.toString(),MainApplication.instance.user.id,"OFF");
                    GlobalScope.launch(Dispatchers.Main) {
                        if(resultOn.first.equals("200")){
                            like_btn.isSelected = false
                        }
                    }
                }
            }else{
                GlobalScope.launch(Dispatchers.Default) {
                    val resultOn = MainApplication.instance.boardRepository.productLikesEdit(id.toString(),MainApplication.instance.user.id,"ON");
                    GlobalScope.launch(Dispatchers.Main) {
                        if(resultOn.first.equals("200")){
                            like_btn.isSelected = true
                        }
                    }
                }

            }
        }


        payment_btn.setOnClickListener {
            var intent = Intent(activity, SelectProductActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("img",img)
            intent.putExtra("category",category)
            intent.putExtra("name",name)
            intent.putExtra("price",price)
            startActivity(intent);
        }

//        if (savedInstanceState == null || !savedInstanceState.getBoolean(PurchaseDetailsFragment.DONT_USE_BACK_BUTTON)) {
//            activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
//        }


        return view
    }

//    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            Navigation.findNavController(view).navigate(R.id.action_back_to_home)
//        }
//    }



companion object {
    const val DONT_USE_BACK_BUTTON = "dont_use_back_button"
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
                KitDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}