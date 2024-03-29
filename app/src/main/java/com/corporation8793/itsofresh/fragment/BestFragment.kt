package com.corporation8793.itsofresh.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.adapter.BestAdapter
import com.corporation8793.itsofresh.decoration.BestDecoration
import com.corporation8793.itsofresh.dto.BestItem
import com.corporation8793.itsofresh.esf_wp.rest.RestClient
import com.corporation8793.itsofresh.esf_wp.rest.data.Post
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
class BestFragment() : Fragment() {
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
        var view = inflater.inflate(R.layout.fragment_best, container, false)


        val kit_list = view.findViewById<RecyclerView>(R.id.best_kit_list)
        val best_progress = view.findViewById<RelativeLayout>(R.id.best_progress)

        val display: DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height: Int = (display.heightPixels / 3.5).toInt()

        var bestAdapter = BestAdapter(context, height, resources.getColor(R.color.category_land_color), findNavController())
        kit_list.adapter = bestAdapter

        val lm = GridLayoutManager(context, 2)
        kit_list.layoutManager = lm

        var divider = BestDecoration(20)
        kit_list.addItemDecoration(divider)
        var count = 1

        if (MainApplication.instance.isWIFIConnected(context!!)){
            GlobalScope.launch(Dispatchers.Default) {
                val item: List<Post> = RestClient.board4BaService.retrievePostInCategories(categories = RestClient.RECIPE_BEST).execute().body()!!
                val id = MainApplication.instance.user.id;

                GlobalScope.launch(Dispatchers.Main) {
                    datas.apply {
                        datas.clear()
                        item.forEach {
                            Log.e("it", it.toString())
                            it.acf.price?.let { it1 -> Log.e("price", it1) }
                            var like = false;
                            if (it.acf.product_likes != false) {
                                var pl = it.acf.product_likes as ArrayList<Int>
                                pl.forEach { i ->
                                    if (i == id.toInt()) {
                                        like = true;
                                        return@forEach
                                    }
                                }
                            }

                            add(BestItem(it.id!!, it.featured_media_src_url, it.title.rendered, it.acf.price + "원", like, count.toString()))

//                        println("상품 카테고리 : ${pr.categories.first().name}")
//                        println("상품명 : ${pr.name} | (주문 id : ${pr.id})")
//                        println("별점 (5.00) : ${pr.average_rating}")
//                        println("상품 이미지 URL : ${pr.images.first().src}")
//                        println("상품 세일 기간 : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
//                        println("상품가격 : ${pr.price}원")
//                        println("재고정보 : ${pr.stock_quantity} / ${pr.acf.total_stock}개")

                            count++
                        }

                        bestAdapter.datas = datas
                        bestAdapter.notifyDataSetChanged()
                        best_progress.visibility = View.GONE
                    }
                }
//                binding.checkText.visibility = View.VISIBLE
            }
    }


//        datas.apply {
//            datas.clear()
//            add(BestItem("0","유기농두부샐러드","12,000원","1","1"))
//            add(BestItem("0","과일그릭요거트보울","12,000원","1","2"))
//            add(BestItem("0","헤이","12,000원","1","3"))
//
//
//            bestAdapter.datas = datas
//            bestAdapter.notifyDataSetChanged()
//        }




        return view
    }

    override fun onResume() {
        super.onResume()
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
                BestFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}