package com.corporation8793.itsofresh.fragment.shop

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.adapter.ShopAdapter
import com.corporation8793.itsofresh.dto.ShopItem
import com.corporation8793.itsofresh.esf_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegionSearchFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<ShopItem>()
    val alldatas = mutableListOf<ShopItem>()

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
        var view = inflater.inflate(R.layout.fragment_search_region, container, false)
        var metropolitan_input_box = view.findViewById<EditText>(R.id.metropolitan_input_box)
        var city_input_box = view.findViewById<EditText>(R.id.city_input_box)


        val shop_list = view.findViewById<RecyclerView>(R.id.shop_list)

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()


        val shopAdapter = ShopAdapter(context, height, resources.getColor(R.color.category_land_color),findNavController())
        shop_list.adapter =  shopAdapter

        val lm = LinearLayoutManager(context)
        shop_list.layoutManager = lm

        view.findViewById<Button>(R.id.search_btn).setOnClickListener {
            Log.e("text",metropolitan_input_box.text.toString().toLowerCase(Locale.getDefault()))
            shopAdapter.region_filter(metropolitan_input_box.text.toString().toLowerCase(Locale.getDefault()),city_input_box.text.toString().toLowerCase(Locale.getDefault()))
        }

        metropolitan_input_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(arg0: Editable) {
                if (arg0.length ==0) {
                    shopAdapter.region_filter(metropolitan_input_box.text.toString().toLowerCase(Locale.getDefault()),city_input_box.text.toString().toLowerCase(Locale.getDefault()))
                }
            }

            override fun beforeTextChanged(
                    arg0: CharSequence, arg1: Int,
                    arg2: Int, arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun onTextChanged(
                    arg0: CharSequence, arg1: Int, arg2: Int,
                    arg3: Int
            ) {
                // TODO Auto-generated method stub
            }
        })

        city_input_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(arg0: Editable) {
                if (arg0.length ==0) {
                    shopAdapter.region_filter(metropolitan_input_box.text.toString().toLowerCase(Locale.getDefault()),city_input_box.text.toString().toLowerCase(Locale.getDefault()))
                }
            }

            override fun beforeTextChanged(
                    arg0: CharSequence, arg1: Int,
                    arg2: Int, arg3: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun onTextChanged(
                    arg0: CharSequence, arg1: Int, arg2: Int,
                    arg3: Int
            ) {
                // TODO Auto-generated method stub
            }
        })

        shop_list.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))

        shop_list.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))

        GlobalScope.launch(Dispatchers.Default) {
            datas.clear()
            alldatas.clear()
            val testId = "test22"
            val testPw = "1234"
            val basicAuth = Credentials.basic(testId, testPw)
            // 저장소 초기화
            val boardRepository = BoardRepository()

            println("====== storeList     ======")

            println("------ listAllStore() -----")
            val listAllStoreResponse = boardRepository.listAllStore()

            listAllStoreResponse.second!!.forEach {
                Log.e("it",it.toString())

                datas.add(ShopItem(it.id,it.featured_media_src_url,it.title.rendered,it.acf.metropolitan+" "+it.acf.address!!))
                alldatas.add(ShopItem(it.id,it.featured_media_src_url,it.title.rendered,it.acf.metropolitan+" "+it.acf.address!!))
            }



            GlobalScope.launch(Dispatchers.Main) {

                shopAdapter.datas = datas
                shopAdapter.alldatas = alldatas
                shopAdapter.notifyDataSetChanged()



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
                RegionSearchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}