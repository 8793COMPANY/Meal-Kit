package com.corporation8793.itsofresh

import android.app.Activity
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor

import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.corporation8793.itsofresh.databinding.DialogOrderCancelBinding
import com.corporation8793.itsofresh.databinding.DialogPointSavedBinding
import com.corporation8793.itsofresh.databinding.DialogReportPostBinding
import com.corporation8793.itsofresh.esf_wp.rest.data.updateOrderBody
import com.corporation8793.itsofresh.esf_wp.rest.repository.Board4BaRepository
import kotlinx.android.synthetic.main.fragment_kit_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class PointSavedDialog(val activity: Activity, val point : Int) : DialogFragment() {
    private var _binding: DialogPointSavedBinding? = null
    private val binding get() = _binding!!
    var rating : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogPointSavedBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 제목, 내용 설정
//        binding.Confirm.text = title
//        binding.customTvContent.text = content
//        binding.cancelBtn.setOnClickListener {
//            buttonClickListener.onButton1Clicked()
//            dismiss()
//        }

        val text = point.toString()+"P 적립되었습니다."
        val spannableStringBuilder = SpannableStringBuilder(text)

        val endIndexExclusive = text.indexOf("적")

        val startIndex = 0

// startIndex ~ endIndexExclusive 에 어떤 색상을 입혀주세요~ 라는 역할을 한다.
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.app_basic_color)),
            startIndex,
            endIndexExclusive-1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
//        spannableStringBuilder.setSpan(
//            AbsoluteSizeSpan(56),
//            startIndex,
//            endIndexExclusive-1,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
//        )

        spannableStringBuilder.setSpan(
            RelativeSizeSpan(1.5f),
            startIndex,
            endIndexExclusive-1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )

        spannableStringBuilder.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndexExclusive-1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )

// 위에서 span 을 입힌 [spannableStringBuilder] 을 textView.text 에 넣어준다


        binding.pointText.setText(spannableStringBuilder)


        binding.okBtn.setOnClickListener {
            dismiss()
            buttonClickListener.onButton1Clicked()
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
        params?.height =  (size.y * 0.31f).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnButtonClickListener {
        fun onButton1Clicked()
    }
    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }
    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener

}