package com.corporation8793.mealkit.fragment.my

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.RatingDialog
import com.corporation8793.mealkit.adapter.PurchaseAdapter
import com.corporation8793.mealkit.decoration.KitDecoration
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.dto.MyItem
import com.corporation8793.mealkit.dto.PurchaseItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.Order
import com.corporation8793.mealkit.esf_wp.rest.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurchaseDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurchaseDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<PurchaseItem>()

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
        val view = inflater.inflate(R.layout.fragment_purchase_details, container, false)

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3).toInt()


        val purchase_list = view.findViewById<RecyclerView>(R.id.purchase_list)
        val purchase_progress = view.findViewById<RelativeLayout>(R.id.purchase_progress)

        val adapter = PurchaseAdapter(parentFragmentManager,activity,context,height,findNavController())

        view.findViewById<Button>(R.id.back_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_back_to_muy)
        }

        purchase_list.adapter = adapter

        val lm = LinearLayoutManager(context)
        purchase_list.layoutManager = lm

        val sharedPreference = context!!.getSharedPreferences("other", 0)
        val id = sharedPreference.getString("id","test22")
        var count = 0


        GlobalScope.launch(Dispatchers.Default) {
            var user_id = RestClient.nonceService.checkUsername(id!!).execute().body()!!.get(0).id
            Log.e("in","start")
            val item = MainApplication.instance.boardRepository.listAllOrder(user_id)
            Log.e("in","end")
            val seconde = item.second!!
            val third = item.third!!
            Log.e("item",item.first)
                datas.apply {
                    seconde.forEach {
                        Log.e("item",it.toString())
//                        Log.e("third",third.get(count).first)


                        var img = MainApplication.instance.boardRepository.retrieveOneProduct(it.line_items.get(0).product_id).second!!.images.get(0).src

                        add(PurchaseItem(img,it.id.toString(),it.line_items.get(0).product_id,it.date_created!!.replace("T"," "),
                               it.meta_data.get(0).value.toString() ,it.line_items.get(0).name.toString(),
                                it.line_items.get(0).total,it.line_items.get(0).quantity,it.billing.address_1+" "+it.billing.address_2))
//                        println("상품 카테고리 : ${pr.categories.first().name}")
//                        println("상품명 : ${pr.name} | (주문 id : ${pr.id})")
//                        println("별점 (5.00) : ${pr.average_rating}")
//                        println("상품 이미지 URL : ${pr.images.first().src}")
//                        println("상품 세일 기간 : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
//                        println("상품가격 : ${pr.price}원")
//                        println("재고정보 : ${pr.stock_quantity} / ${pr.acf.total_stock}개")
//                        println("---------------")
                        count++
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                    adapter.datas = datas
                    adapter.notifyDataSetChanged()
                        purchase_progress.visibility = View.GONE
                }
            }
//                binding.checkText.visibility = View.VISIBLE
        }



        var divider = KitDecoration(20)
        purchase_list.addItemDecoration(divider)

        return view
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PurchaseDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PurchaseDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}