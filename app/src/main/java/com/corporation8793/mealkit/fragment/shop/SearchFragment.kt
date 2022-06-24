package com.corporation8793.mealkit.fragment.shop

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.BestAdapter
import com.corporation8793.mealkit.adapter.KitAdapter
import com.corporation8793.mealkit.adapter.ShopAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.dto.ShopItem
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<ShopItem>()

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
        var view = inflater.inflate(R.layout.fragment_search, container, false)



        val shop_list = view.findViewById<RecyclerView>(R.id.shop_list)

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()


        val shopAdapter = ShopAdapter(context, height, resources.getColor(R.color.category_land_color),findNavController())
        shop_list.adapter =  shopAdapter

        val lm = LinearLayoutManager(context)
        shop_list.layoutManager = lm

        shop_list.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))

        datas.apply {
            GlobalScope.launch(Dispatchers.Default) {
                val testId = "test22"
                val testPw = "1234"
                val basicAuth = Credentials.basic(testId, testPw)
                // 저장소 초기화
                val boardRepository = BoardRepository()

                println("====== storeList     ======")

                println("------ listAllStore() -----")
                val listAllStoreResponse = boardRepository.listAllStore()

                GlobalScope.launch(Dispatchers.Main) {
                    datas.apply {
                        listAllStoreResponse.second!!.forEach {

                            add(ShopItem(it.id,it.featured_media_src_url,it.title.rendered,it.acf.address!!))
                        }
                        shopAdapter.datas = datas
                        shopAdapter.notifyDataSetChanged()
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
                SearchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}