package com.corporation8793.mealkit.fragment.shop

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.*
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.dto.BestItem
import com.corporation8793.mealkit.dto.ShopItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.DisableProduct
import com.corporation8793.mealkit.esf_wp.rest.repository.Board4BaRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
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
    var argument_check = false
    lateinit var bottomSheetDialog :BottomSheetDialog

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

        if (arguments!=null)
            argument_check = true




       val mapView = view.findViewById<MapView>(R.id.map_fragment)
       mapView.getMapAsync(this)


        //레트로핏 객체 생성
        val retrofit = Retrofit.Builder().
        baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/").
        addConverterFactory(GsonConverterFactory.create()).
        build()


        if (argument_check){
            setMarker(retrofit,arguments!!.getString("address")!!,arguments!!.getString("shop_name")!!)
        }



//        bottomSheetDialog.show()

        return view
    }

    fun setMarker(retrofit:Retrofit, address : String, shop_name : String){
        val APIKEY_ID = "9l8w6ft1l8"
        val APIKEY = "aTjzwzUWg7lzXcOmxTY9H7s3N8jZbF3OBUgUIuWR"

        val api = retrofit.create(NaverAPI::class.java)
        //근처에서 길찾기
        val callgetPath = api.getPath(APIKEY_ID, APIKEY,address)


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
                        return shop_name
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
                    val bottomSheetView = layoutInflater.inflate(R.layout.dialog_shop_info, null)
                    var shop_name_textview =bottomSheetView.findViewById<TextView>(R.id.shop_name)
                    var shop_imageview =bottomSheetView.findViewById<ImageView>(R.id.shop_img)
                    var shop_imageview2 =bottomSheetView.findViewById<ImageView>(R.id.shop_img2)
                    var shop_info_textview =bottomSheetView.findViewById<TextView>(R.id.shop_info)

                    dataSetting(shop_name,shop_name_textview,shop_imageview,shop_imageview2,shop_info_textview)

                    bottomSheetDialog = BottomSheetDialog(context!!)
                    bottomSheetDialog.setContentView(bottomSheetView)
                    bottomSheetDialog.show()

                    true
                }

                marker.onClickListener = listener

            }

            override fun onFailure(call: Call<ResultGeo>, t: Throwable) {
                Log.e("t",t.message.toString())
            }

        })
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

    fun dataSetting(shop_name : String, shop_name_textview : TextView, shop_imgview : ImageView, shop_imgview2 : ImageView, shop_info_textview : TextView) {
        var shop = ""
        var img = ""
        var img2 = ""
        var info =""
        GlobalScope.launch(Dispatchers.Default) {
            val testId = "test22"
            val testPw = "1234"
            val basicAuth = Credentials.basic(testId, testPw)
            // 저장소 초기화
            val boardRepository = BoardRepository()

            val listAllStoreResponse = boardRepository.listAllStore()
            println("listAllStore : ${listAllStoreResponse.first}, ${listAllStoreResponse.second}")

            // 물품 필터링
            println("------ Filtering     -----")
            val notSaleStore = listAllStoreResponse.second?.filter { it.title.rendered.equals(shop_name) }
            println("가게: ")

            if (notSaleStore != null) {
                for ((i, ns) in notSaleStore.withIndex()) {
                    println("${i+1}. ${ns.title.rendered}")
                    shop = ns.title.rendered
                    img = ns.featured_media_src_url
                    img2 = ns.acf.feature_image_2!!
                    info = replaceText(ns.content.rendered)
                }
            }

            println("====== storeList     ======")

            println("------ listAllStore() -----")

            GlobalScope.launch(Dispatchers.Main) {
                shop_name_textview.setText(shop)
                shop_info_textview.setText(info)
                Glide.with(context!!).load(img).into(shop_imgview)
                Glide.with(context!!).load(img2).into(shop_imgview2)
            }

            }
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