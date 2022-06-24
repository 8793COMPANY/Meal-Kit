package com.corporation8793.mealkit.fragment.shop

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.BestItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment() : Fragment() , OnMapReadyCallback{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val datas = mutableListOf<BestItem>()
    lateinit var naverMap: NaverMap

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
        var view = inflater.inflate(R.layout.fragment_map, container, false)

        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_shop_info, null)
        val bottomSheetDialog = BottomSheetDialog(context!!)
        bottomSheetDialog.setContentView(bottomSheetView)

       val mapView = view.findViewById<MapView>(R.id.map_fragment)
       mapView.getMapAsync(this)

        val APIKEY_ID = "9l8w6ft1l8"
        val APIKEY = "aTjzwzUWg7lzXcOmxTY9H7s3N8jZbF3OBUgUIuWR"
        //레트로핏 객체 생성
        val retrofit = Retrofit.Builder().
        baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/").
        addConverterFactory(GsonConverterFactory.create()).
        build()

        val api = retrofit.create(NaverAPI::class.java)
        //근처에서 길찾기
        val callgetPath = api.getPath(APIKEY_ID, APIKEY,"광주광역시 동구 동계천로 150")


        callgetPath.enqueue(object : Callback<ResultGeo> {
            override fun onResponse(
                    call: Call<ResultGeo>,
                    response: Response<ResultGeo>
            ) {
                var status = response.body()?.status
                var address = response.body()?.addresses!!
                var x = address.get(0).x
                var y = address.get(0).y
                Log.e("x",address.get(0).x.toString())
                Log.e("y",address.get(0).y.toString())
                Log.e("status",status.toString())
                Log.e("marker","in!")

                val marker = Marker()
                marker.icon = OverlayImage.fromResource( R.drawable.custom_marker)
                marker.position = LatLng(y, x)
                marker.map = naverMap

                val infoWindow = InfoWindow()
                infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(context!!) {
                    override fun getText(infoWindow: InfoWindow): CharSequence {
                        return "정보 창 내용"
                    }
                }

                infoWindow.open(marker)

                //경로 시작점으로 화면 이동
                if(marker!= null) {
                    val cameraUpdate = CameraUpdate.scrollTo(marker.position)
                            .animate(CameraAnimation.Fly, 3000)
                    naverMap!!.moveCamera(cameraUpdate)

                }

                val listener = Overlay.OnClickListener { overlay ->
                    val marker = overlay as Marker

                    bottomSheetDialog.show()

                    true
                }

                marker.onClickListener = listener

            }

            override fun onFailure(call: Call<ResultGeo>, t: Throwable) {
                Log.e("t",t.message.toString())
            }

        })




//        bottomSheetDialog.show()

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
                MapFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onMapReady(p0: NaverMap) {
        Log.e("onMapReady","in!")
        naverMap = p0
    }
}