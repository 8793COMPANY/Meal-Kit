package com.corporation8793.mealkit.fragment

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.RecipeAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.RecipeItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.Post
import kotlinx.android.synthetic.main.activity_write_recipe.view.*
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
class RecipeFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<RecipeItem>()

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
        var view = inflater.inflate(R.layout.fragment_recipe, container, false)
        var id = arguments?.getString("id")
        Log.e("id",id!!)
        view.findViewById<Button>(R.id.back_btn).setOnClickListener {
            findNavController().popBackStack()
        }

        GlobalScope.launch(Dispatchers.Default) {
            val item : Post = RestClient.board4BaService.retrieveOnePost(id).execute().body()!!

            GlobalScope.launch(Dispatchers.Main) {
                Glide.with(requireActivity()).load(item.featured_media_src_url).into(view.findViewById(R.id.recipe_img))
             view.findViewById<TextView>(R.id.recipe_name).setText(item.title.rendered)
                view.findViewById<TextView>(R.id.recipe_info).setText(replaceText(item.excerpt.rendered))
                view.findViewById<TextView>(R.id.use_kit).setText("사용 밀키트:"+item.acf.product)
                view.findViewById<TextView>(R.id.detailed_recipe).setText(replaceText(item.content.rendered))
                Log.e("content",item.content.rendered)
                view.findViewById<TextView>(R.id.detailed_recipe).setText(Html.fromHtml((item.content.rendered)))

            }

        }




        return view
    }


    fun replaceText(text : String) : String{
        val regex = Regex("<div id=\"modal-ready\">")
        val matchResult: MatchResult? = regex.find(text)
//        println("match value: ${matchResult?.value}")
        var result = text
        matchResult?.groupValues?.forEach {
            Log.e("match value", it)
            result = result.replace(it,"")
        }

        return result.replace("<p>","").replace("</p>","")
                .replace("<ul>","").replace("</ul>","")
                .replace("<li>","").replace("</li>","")
                .replace("<br>","").replace("<br />","")
                .replace("<strong>","").replace("</strong>","")
                .replace("<div>","").replace("</div>","")


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
                RecipeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}