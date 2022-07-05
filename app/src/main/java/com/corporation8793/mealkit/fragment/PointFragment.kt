package com.corporation8793.mealkit.fragment

import android.graphics.Point
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.BestAdapter
import com.corporation8793.mealkit.adapter.PointAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.PointItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.Post
import com.corporation8793.mealkit.esf_wp.rest.repository.Board4BaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
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
class PointFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<PointItem>()

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
        var view = inflater.inflate(R.layout.fragment_point, container, false)


        val point_list = view.findViewById<RecyclerView>(R.id.point_list)
        var total_point_count = view.findViewById<TextView>(R.id.total_point_count)
        var total_point_box = view.findViewById<TextView>(R.id.total_point_box)

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 9.5).toInt()

        var pointAdapter = PointAdapter(context, height)
        point_list.adapter = pointAdapter

        point_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        val lm = LinearLayoutManager(context)
        point_list.layoutManager = lm

        var divider = BestDecoration(10)
        point_list.addItemDecoration(divider)

        view.findViewById<View>(R.id.action_bar).findViewById<TextView>(R.id.title).text = "포인트"
        view.findViewById<View>(R.id.action_bar).findViewById<Button>(R.id.back_btn).visibility = View.INVISIBLE


        total_point_box.setText(
            NumberFormat.getInstance(Locale.getDefault()).format( MainApplication.instance.user.meta_data?.filter {
                    metaData -> metaData.key =="point"}?.first()?.value.toString().toInt())
           )

        GlobalScope.launch(Dispatchers.Default) {
         val retrievePointLogResponse = MainApplication.instance.board4BaRepository.retrievePointLog(
            author = MainApplication.instance.user.id,
            categories = RestClient.POINT_LOG
        )

            val validUserResponse = MainApplication.instance.nonceRepository.getValidUserInfo(MainApplication.instance.user.id)

            for ((i, post) in retrievePointLogResponse.second?.withIndex()!!) {
                datas.add( PointItem(post.title.rendered,post.date.toString().replace("T"," "),post.excerpt.rendered,"1"))
            }

            GlobalScope.launch(Dispatchers.Main) {
                total_point_count.setText("${retrievePointLogResponse.second?.size}건")
                total_point_box.setText(
                    NumberFormat.getInstance(Locale.getDefault()).format( validUserResponse.second?.meta_data?.filter {
                            metaData -> metaData.key =="point"}?.first()?.value.toString().toInt())
                )
                pointAdapter.datas = datas
                pointAdapter.notifyDataSetChanged()
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
                PointFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}