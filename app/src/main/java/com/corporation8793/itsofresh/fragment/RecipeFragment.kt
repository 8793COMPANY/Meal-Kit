package com.corporation8793.itsofresh.fragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.dto.RecipeItem
import com.corporation8793.itsofresh.esf_wp.rest.RestClient
import com.corporation8793.itsofresh.esf_wp.rest.data.Post
import kotlinx.android.synthetic.main.activity_write_recipe.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

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
        var recipe_like_btn = view.findViewById<ImageView>(R.id.recipe_like_btn);
        var id = arguments?.getString("id")
        var like = arguments?.getBoolean("like")
        var category = arguments?.getString("category")

        var report_text  = view.findViewById<TextView>(R.id.report_text)

        recipe_like_btn.isSelected = like!!;


        if (category!!.equals("best"))
            report_text.visibility = View.GONE


        recipe_like_btn.setOnClickListener {
            if (recipe_like_btn.isSelected){
//                recipe_like_btn.isSelected = false

                GlobalScope.launch(Dispatchers.Default) {
                    val resultOn = MainApplication.instance.board4BaRepository.updatePostLikes(id.toString(),MainApplication.instance.user.id,"OFF");
                    GlobalScope.launch(Dispatchers.Main) {
                        if(resultOn.first.equals("200")){
                            recipe_like_btn.isSelected = false
                        }
                    }
                }
            }else{
//                recipe_like_btn.isSelected = true
                GlobalScope.launch(Dispatchers.Default) {
                    val resultOn = MainApplication.instance.board4BaRepository.updatePostLikes(id.toString(),MainApplication.instance.user.id,"ON");
                    GlobalScope.launch(Dispatchers.Main) {
                        if(resultOn.first.equals("200")){
                            recipe_like_btn.isSelected = true
                        }
                    }
                }

            }
        }

        report_text.setOnClickListener {
            val dialog = ReportDialog(requireActivity(),id!!)
            dialog.show(parentFragmentManager!!,"hello")
        }


        Log.e("id",id!!)
        view.findViewById<Button>(R.id.back_btn).setOnClickListener {
            findNavController().popBackStack()
        }

        GlobalScope.launch(Dispatchers.Default) {
            val item : Post = RestClient.board4BaService.retrieveOnePost(id).execute().body()!!

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    Glide.with(requireActivity()).load(item.featured_media_src_url)
                        .into(view.findViewById(R.id.recipe_img))
                    view.findViewById<TextView>(R.id.recipe_name).setText(item.title.rendered)
                    view.findViewById<TextView>(R.id.recipe_info)
                        .setText(replaceText(item.excerpt.rendered))
                    view.findViewById<TextView>(R.id.use_kit).setText("사용 밀키트:" + item.acf.product)
                    view.findViewById<TextView>(R.id.detailed_recipe)
                        .setText(replaceText(item.content.rendered))
                    Log.e("content", item.content.rendered)
                    view.findViewById<TextView>(R.id.detailed_recipe)
                        .setText(Html.fromHtml((item.content.rendered)))
                }catch (e : IllegalStateException){
                    Log.e("e","error~")
                }

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