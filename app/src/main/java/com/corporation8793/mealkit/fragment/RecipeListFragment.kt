package com.corporation8793.mealkit.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.adapter.RecipeAdapter
import com.corporation8793.mealkit.decoration.BestDecoration
import com.corporation8793.mealkit.dto.RecipeItem

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



        val display : DisplayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3.5).toInt()

        var recipeAdapter = RecipeAdapter(context, height, resources.getColor(R.color.category_land_color), findNavController())
        recipe_list.adapter = recipeAdapter

        val lm = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recipe_list.layoutManager = lm

        var divider = BestDecoration(20)
        recipe_list.addItemDecoration(divider)

        datas.apply {
            datas.clear()
            add(RecipeItem("0","단호박스프","진한 맛의 단호박 스프","1","1","5"))
            add(RecipeItem("0","단호박스프","진한 맛의 풍미를 느낄 수 있는\n" +
                    "신선한 바질 시금치 스프를\n" +
                    "집에서 간편히","1","1","5"))
            add(RecipeItem("0","단호박스프","진한 맛의 단호박 스프","1","1","5"))
            add(RecipeItem("0","단호박스프","진한 맛의 단호박 스프","1","1","5"))


            recipeAdapter.datas = datas
            recipeAdapter.notifyDataSetChanged()
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
                RecipeListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}