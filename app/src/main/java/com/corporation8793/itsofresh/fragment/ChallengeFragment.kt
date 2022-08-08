package com.corporation8793.itsofresh.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.corporation8793.itsofresh.*
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
 * Use the [ChallengeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChallengeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var video_ending_check = true

    val saved_point = mapOf(1 to 10,2 to 30, 3 to 50,4 to 70,5 to 100,)

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
        val view = inflater.inflate(R.layout.fragment_challenge, container, false)
        val videoview = view.findViewById<VideoView>(R.id.videoview)
        var uri = Uri.parse("android.resource://"+requireActivity().packageName+"/"+R.raw.point_10)


        val random = Random()
        val num = random.nextInt(5)+1

        when(num){
            1-> {uri = Uri.parse("android.resource://"+requireActivity().packageName+"/"+R.raw.point_10)
//                insertPoint(10,"룰렛 포인트")

            }
            2->{uri = Uri.parse("android.resource://"+requireActivity().packageName+"/"+R.raw.point_30)
//                insertPoint(30,"룰렛 포인트")

            }
            3->{uri = Uri.parse("android.resource://"+requireActivity().packageName+"/"+R.raw.point_50)
//                insertPoint(50,"룰렛 포인트")
            }
            4->{uri = Uri.parse("android.resource://"+requireActivity().packageName+"/"+R.raw.point_70)
//                insertPoint(70,"룰렛 포인트")

            }
            5->{uri = Uri.parse("android.resource://"+requireActivity().packageName+"/"+R.raw.point_100)


            }
        }

        videoview.setVideoURI(uri)
        videoview.seekTo(1)


        view.findViewById<Button>(R.id.go_btn).setOnClickListener {
            if (video_ending_check) {
                video_ending_check = false
                videoview.start()
                insertPoint(saved_point.get(num)!!,"룰렛 포인트")
            }
        }


        view.findViewById<Button>(R.id.back_btn).setOnClickListener {
            if (video_ending_check)
                Navigation.findNavController(view).navigate(R.id.action_back_to_home)
        }

        videoview.setOnCompletionListener {
//            video_ending_check = true
            val dialog = PointSavedDialog(requireActivity(),saved_point.get(num)!!)
            dialog.setButtonClickListener(object: PointSavedDialog.OnButtonClickListener{
                override fun onButton1Clicked() {

                    findNavController().popBackStack()
                }

            })
            dialog.show(parentFragmentManager!!,"hello")
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
         * @return A new instance of fragment ChallengeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ChallengeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                    if (video_ending_check)
                        findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }


    fun insertPoint(point: Int, log:String){


        GlobalScope.launch(Dispatchers.Default) {
            val pointResult= MainApplication.instance.nonceRepository.editPoint(
                MainApplication.instance.board4BaRepository,
                MainApplication.instance.user.id,point,"+",log)
            if(pointResult.first.equals("200")){
                MainApplication.instance.setPedometerSuccessCount("point_roulette",2);
            }
        }
    }
}