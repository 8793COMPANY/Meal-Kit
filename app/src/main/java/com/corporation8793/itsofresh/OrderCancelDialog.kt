package com.corporation8793.itsofresh

import android.app.Activity
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast

import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.corporation8793.itsofresh.databinding.DialogOrderCancelBinding
import com.corporation8793.itsofresh.databinding.DialogReportPostBinding
import com.corporation8793.itsofresh.esf_wp.rest.data.updateOrderBody
import com.corporation8793.itsofresh.esf_wp.rest.repository.Board4BaRepository
import kotlinx.android.synthetic.main.fragment_kit_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class OrderCancelDialog(val activity: Activity, val id : String) : DialogFragment() {
    private var _binding: DialogOrderCancelBinding? = null
    private val binding get() = _binding!!
    var rating : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogOrderCancelBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 제목, 내용 설정
//        binding.Confirm.text = title
//        binding.customTvContent.text = content
        binding.cancelBtn.setOnClickListener {
            buttonClickListener.onButton1Clicked()
            dismiss()
        }



        binding.okBtn.setOnClickListener {

            GlobalScope.launch(Dispatchers.Default) {
                    Log.e("in!","cancel btn")
                    var result = MainApplication.instance.nonceRepository.updateOrder(id, updateOrderBody("request-cancel"))
                    Log.e("result",result.first)
                    GlobalScope.launch(Dispatchers.Main) {
                        Toast.makeText(context, "주문 취소가 완료되었습니다", Toast.LENGTH_SHORT).show()
                        buttonClickListener.onButton2Clicked()
                        dismiss()
                    }
            }
//            binding.waitingMsg.visibility = View.VISIBLE
//            GlobalScope.launch(Dispatchers.Default) {
//
//                val testId = "esffnb"
//                val testPw = "@esfadmin*0967"
//                val basicAuth = Credentials.basic(testId, testPw)
//                val boardRepository = Board4BaRepository(basicAuth)
//                val updatePost = boardRepository.updatePost(id)
//                println("Update Post : ${updatePost}\n")
//
//                GlobalScope.launch(Dispatchers.Main) {
//                    binding.waitingMsg.visibility = View.GONE
//                    dismiss()
//                    if (updatePost == "200") {
//                        Toast.makeText(context, "게시물 신고가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//
//                        findNavController().popBackStack()
//                    }else{
//                        Toast.makeText(context, "게시물 신고에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }

        }





//
//        // 취소 버튼
//        binding.customTvBtn1.setOnClickListener {
//            dismiss()
//        }
//        // 확인 버튼
//        binding.customTvBtn2.setOnClickListener {
//            dismiss()
//        }


//
//        // Generate custom width and height and
//        // add to the dialog attributes
//        // we multiplied the width and height by 0.5,
//        // meaning reducing the size to 50%
//        val mLayoutParams = WindowManager.LayoutParams()
//        mLayoutParams.width = (size.x * 0.9f).toInt()
//        mLayoutParams.height = (size.y * 0.6f).toInt()
//
//        dialog!!.window?.attributes = mLayoutParams

        return view
    }

    override fun onResume() {
        super.onResume()
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.85f).toInt()
        params?.height =  (size.y * 0.22f).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonClickListener {
        fun onButton1Clicked()
        fun onButton2Clicked()
        fun onButton3Clicked()
    }
    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }
    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener

}