package com.corporation8793.itsofresh.fragment

import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.adapter.PointAdapter
import com.corporation8793.itsofresh.decoration.BestDecoration
import com.corporation8793.itsofresh.dto.PointItem
import com.corporation8793.itsofresh.esf_wp.rest.RestClient
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
    var point_list_count =0

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


        val point_list_view = view.findViewById<ConstraintLayout>(R.id.point_list_view)
        val point_list = view.findViewById<RecyclerView>(R.id.point_list)
        var total_point_count = view.findViewById<TextView>(R.id.total_point_count)
        var total_point_box = view.findViewById<TextView>(R.id.total_point_box)
        var down_icon = view.findViewById<Button>(R.id.down_icon)
//        val spinner = view.findViewById<Spinner>(R.id.spinner)

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

//        val items = resources.getStringArray(R.array.count)
//        val myAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, items)
//
//        spinner.adapter = myAdapter
//        spinner.setSelection(0)

        if ( NumberFormat.getInstance(Locale.getDefault()).format( MainApplication.instance.user.meta_data?.filter {
                    metaData -> metaData.key =="point"}?.first()?.value.toString().toInt())< "0"){
            total_point_box.setText(
                "0"
            )
        }else{
            total_point_box.setText(
                NumberFormat.getInstance(Locale.getDefault()).format( MainApplication.instance.user.meta_data?.filter {
                        metaData -> metaData.key =="point"}?.first()?.value.toString().toInt())
            )
        }



        total_point_count.setOnClickListener {

            Log.e("scaleY",down_icon.scaleY.toString())
            if(point_list_view.isVisible) {
                point_list_view.visibility = View.INVISIBLE
                down_icon.scaleY = 1f
            }else {
                point_list_view.visibility = View.VISIBLE
                down_icon.scaleY = -1f
            }
        }

        GlobalScope.launch(Dispatchers.Default) {

            Log.e("list","start")

         val retrievePointLogResponse = MainApplication.instance.board4BaRepository.retrievePointLog(
            author = MainApplication.instance.user.id,
            categories = RestClient.POINT_LOG
        )
//            GlobalScope.launch(Dispatchers.Main) {
//                total_point_count.setText(retrievePointLogResponse.second?.size.toString()+"건")
//            }
//            Log.e("retrievePointLogResponse",retrievePointLogResponse.second?.size.toString())

            val validUserResponse = MainApplication.instance.nonceRepository.getValidUserInfo(MainApplication.instance.user.id)
            Log.e("validUserResponse","end")

            for ((i, post) in retrievePointLogResponse.second?.withIndex()!!) {
                var point = Html.fromHtml(post.excerpt.rendered).toString().replace(" ","").replace("-","")
                    .replace("+","").replace("–","")
                    .replace("+","").replace("–","")
                if(point.length != 3 && point.contains("0")) {
                    datas.add(
                        PointItem(
                            post.title.rendered,
                            post.date.toString().replace("T", " "),
                            post.excerpt.rendered,
                            "1"
                        )
                    )
                    point_list_count++
                }
            }

            GlobalScope.launch(Dispatchers.Main) {
                total_point_count.setText(point_list_count.toString()+"건")
                var data =         NumberFormat.getInstance(Locale.getDefault()).format( validUserResponse.second?.meta_data?.filter {
                        metaData -> metaData.key =="point"}?.first()?.value.toString().toInt())
                Log.e("data",data)
                if(data <= "0")
                    data = "0"
                total_point_box.setText(data)
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