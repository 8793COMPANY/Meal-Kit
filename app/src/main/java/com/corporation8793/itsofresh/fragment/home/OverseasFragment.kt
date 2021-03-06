package com.corporation8793.itsofresh.fragment.home

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.adapter.KitAdapter
import com.corporation8793.itsofresh.decoration.KitDecoration
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.dto.KitItem
import com.corporation8793.itsofresh.esf_wp.rest.RestClient
import com.corporation8793.itsofresh.esf_wp.rest.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LandFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverseasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val datas = mutableListOf<KitItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_overseas, container, false)

        val kit_list = view.findViewById<RecyclerView>(R.id.kit_list)



        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 6.5).toInt()

        var kitAdapter = KitAdapter(context, height, resources.getColor(R.color.category_overseas_color),findNavController())
        kit_list.adapter = kitAdapter

        val lm = LinearLayoutManager(context)
        kit_list.layoutManager = lm

        var divider = KitDecoration(20)
        kit_list.addItemDecoration(divider)

        if(MainApplication.instance.isWIFIConnected(context!!)) {

            GlobalScope.launch(Dispatchers.Default) {
                val item: List<Product> = RestClient.boardService.listAllProduct(RestClient.PRODUCT_OVERSEAS).execute().body()!!
                val id = MainApplication.instance.user.id;

                GlobalScope.launch(Dispatchers.Main) {
                    datas.apply {
                        item.forEach {
                            Log.e("it", it.toString())
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
                            add(KitItem(it.id,it.images.first().src, it.date_on_sale_from,it.date_on_sale_to, "????????? ??????",
                                    it.name, it.price, it.stock_quantity, it.acf.total_stock!!,like,
                                    it.short_description))
                        }


//                        println("?????? ???????????? : ${pr.categories.first().name}")
//                        println("????????? : ${pr.name} | (?????? id : ${pr.id})")
//                        println("?????? (5.00) : ${pr.average_rating}")
//                        println("?????? ????????? URL : ${pr.images.first().src}")
//                        println("?????? ?????? ?????? : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
//                        println("???????????? : ${pr.price}???")
//                        println("???????????? : ${pr.stock_quantity} / ${pr.acf.total_stock}???")
//                        println("---------------")

                        kitAdapter.datas = datas
                        kitAdapter.notifyDataSetChanged()
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
         * @return A new instance of fragment LandFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OverseasFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}