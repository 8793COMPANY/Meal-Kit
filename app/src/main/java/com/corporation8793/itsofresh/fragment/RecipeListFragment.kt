package com.corporation8793.itsofresh.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.itsofresh.*
import com.corporation8793.itsofresh.activity.WriteRecipeActivity
import com.corporation8793.itsofresh.adapter.RecipeAdapter
import com.corporation8793.itsofresh.decoration.BestDecoration
import com.corporation8793.itsofresh.dto.RecipeItem
import com.corporation8793.itsofresh.esf_wp.rest.RestClient
import com.corporation8793.itsofresh.esf_wp.rest.data.Post
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
class RecipeListFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<RecipeItem>()
    val alldatas = mutableListOf<RecipeItem>()
    lateinit var recipeAdapter : RecipeAdapter
    lateinit var recipelist_progress : RelativeLayout
    var lifecycler_check = true

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
        var view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        val recipe_list = view.findViewById<RecyclerView>(R.id.recipe_list)
        val go_recipe_write_btn = view.findViewById<FloatingActionButton>(R.id.go_recipe_write_btn)
        recipelist_progress = view.findViewById<RelativeLayout>(R.id.recipelist_progress);
        var comment_input_box = view.findViewById<EditText>(R.id.comment_input_box);



        go_recipe_write_btn.setOnClickListener {
            var intent = Intent(activity, WriteRecipeActivity::class.java)
            startActivity(intent)
        }

        lifecycler_check = false

        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3.5).toInt()

        recipeAdapter = RecipeAdapter(context, height, resources.getColor(R.color.category_land_color), findNavController())
        recipe_list.adapter = recipeAdapter

        val lm = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recipe_list.layoutManager = lm

        var divider = BestDecoration(20)
        recipe_list.addItemDecoration(divider)

        comment_input_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(arg0: Editable) {
                val text: String = comment_input_box.getText().toString()
                    .toLowerCase(Locale.getDefault())
                recipeAdapter.filter(text)
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



//        GlobalScope.launch(Dispatchers.Default) {
//            recipelist_progress.visibility = View.VISIBLE
//            val item: List<Post> =
//                RestClient.board4BaService.retrievePostInCategories(categories = RestClient.RECIPE_CUSTOMER)
//                    .execute().body()!!
//
//                val id = MainApplication.instance.user.id
//
//                Log.e("item total log !!", item.toString())
//
//                alldatas.clear()
//                datas.clear()
//
//                Log.e("item", item.size.toString())
//
//
////                item.forEach {
////                    Log.e("it", it.toString())
//                    //Log.e("price",it.featured_media_src_url)
//                    var like_count = "0"
////                        val authorData = RestClient.nonceService.getValidUserInfo(it.author).execute().body()!!
//                    Log.e("hi", "in")
//                    //val filteredData = authorData.meta_data?.filter { metaData -> metaData.key == "profile_img" }
//
////<<<<<<< HEAD
////                        var authorImage = RestClient.board4BaService.retrieveMedia(filteredData?.first()?.value.toString()).execute().body()!!
////                        println("Author Profile Image URL : ${authorImage.guid?.rendered}\n")
////=======
//                    //var authorImage = RestClient.board4BaService.retrieveMedia(filteredData?.first()?.value.toString()).execute().body()!!
////                        var authorImage = authorData.meta_data?.filter { metaData -> metaData.key == "profile_img" }!!.first().value.toString()
////                        println("Author Profile Image URL : ${authorImage}\n")
//
////                        if (pr.acf.product_likes.toString()!! == null)
////                            like_count = "0"
////                        else
////                            like_count = pr.acf.product_likes.toString()
//
////                        var like = false;
////                        if(it.acf.product_likes != null && it.acf.product_likes !is Boolean) {
////                            var pl = it.acf.product_likes as ArrayList<Int>
////                            pl.forEach { i ->
////                                if(i == id.toInt()){
////                                    like = true;
////                                    return@forEach
////                                }
//                    GlobalScope.launch(Dispatchers.Main) {
//
//                        item.forEach {
//                            Log.e("it", it.toString())
//
//                            var like_count = "0"
//                            val profile_img = it.acf.profile_img
//
//                            var like = false
//                            if (it.acf.product_likes != null && it.acf.product_likes !is Boolean) {
//                                var pl = it.acf.product_likes as ArrayList<Int>
//                                pl.forEach { i ->
//                                    if (i == id.toInt()) {
//                                        like = true;
//                                        return@forEach
//                                    }
//                                }
//
//
////
////                        datas.add(RecipeItem(it.id!!,"",it.title.rendered,replaceText(it.excerpt.rendered),"",like,"0"))
////
////                        alldatas.add(RecipeItem(it.id!!,"",it.title.rendered,replaceText(it.excerpt.rendered),"",like,"0"))
//
//
////                        datas.add(RecipeItem(it.id!!,it.featured_media_src_url,it.title.rendered,replaceText(it.excerpt.rendered),authorImage.guid?.rendered!!,like,"0"))
////
////                        alldatas.add(RecipeItem(it.id!!,it.featured_media_src_url,it.title.rendered,replaceText(it.excerpt.rendered),authorImage.guid?.rendered!!,like,"0"))
//
////                        datas.add(RecipeItem(it.id!!,it.featured_media_src_url,it.title.rendered,replaceText(it.excerpt.rendered),"",like,"0"))
////
////                        alldatas.add(RecipeItem(it.id!!,it.featured_media_src_url,it.title.rendered,replaceText(it.excerpt.rendered),"",like,"0"))
//
//
//                                //                        println("상품 카테고리 : ${pr.categories.first().name}")
////                        println("상품명 : ${pr.name} | (주문 id : ${pr.id})")
////                        println("별점 (5.00) : ${pr.average_rating}")
////                        println("상품 이미지 URL : ${pr.images.first().src}")
////                        println("상품 세일 기간 : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
////                        println("상품가격 : ${pr.price}원")
////                        println("재고정보 : ${pr.stock_quantity} / ${pr.acf.total_stock}개")
////                        println("---------------")
//
//                            } else {
//                                like = false
//
//                            }
//                            Log.e("랄라", profile_img)
//                            datas.add(
//                                RecipeItem(
//                                    it.id!!,
//                                    it.featured_media_src_url,
//                                    it.title.rendered,
//                                    replaceText(it.excerpt.rendered),
//                                    profile_img,
//                                    like,
//                                    "0"
//                                )
//                            )
//
//                            alldatas.add(
//                                RecipeItem(
//                                    it.id!!,
//                                    it.featured_media_src_url,
//                                    it.title.rendered,
//                                    replaceText(it.excerpt.rendered),
//                                    profile_img,
//                                    like,
//                                    "0"
//                                )
//                            )
//
//                        }
//
//                        recipeAdapter.datas = datas
//                        recipeAdapter.alldatas = alldatas
//                        recipeAdapter.notifyDataSetChanged()
//                        recipelist_progress.visibility = View.GONE;
//                    }
//
////                }
//
//        }





        return view
    }

    override fun onResume() {
        super.onResume()
        if (recipeAdapter != null) {
            recipelist_progress.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.Default) {

                val item: List<Post> =
                    RestClient.board4BaService.retrievePostInCategories(categories = RestClient.RECIPE_CUSTOMER)
                        .execute().body()!!
                Log.e("item", item.size.toString())

                val id = MainApplication.instance.user.id

                Log.e("item total log !!", item.toString())

                alldatas.clear()
                datas.clear()
//                Log.e("item", item.size.toString())

                GlobalScope.launch(Dispatchers.Main) {

                    item.forEach {
                        Log.e("it", it.toString())

                        var like_count = "0"
                        val profile_img = it.acf.profile_img

                        var like = false
                        if (it.acf.product_likes != null && it.acf.product_likes !is Boolean) {
                            var pl = it.acf.product_likes as ArrayList<Int>
                            pl.forEach { i ->
                                if (i == id.toInt()) {
                                    like = true;
                                    return@forEach
                                }
                            }

                        } else {
                            like = false

                        }
                        Log.e("랄라", profile_img)
                        datas.add(
                            RecipeItem(
                                it.id!!,
                                it.featured_media_src_url,
                                it.title.rendered,
                                replaceText(it.excerpt.rendered),
                                profile_img,
                                like,
                                "0"
                            )
                        )

                        alldatas.add(
                            RecipeItem(
                                it.id!!,
                                it.featured_media_src_url,
                                it.title.rendered,
                                replaceText(it.excerpt.rendered),
                                profile_img,
                                like,
                                "0"
                            )
                        )

                    }

                    recipeAdapter.datas = datas
                    recipeAdapter.alldatas = alldatas
                    recipeAdapter.notifyDataSetChanged()
                    recipelist_progress.visibility = View.GONE;
                }


            }
        }
    }


    fun replaceText(text : String) : String{
        val regex = Regex("&.*;")
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
                RecipeListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}