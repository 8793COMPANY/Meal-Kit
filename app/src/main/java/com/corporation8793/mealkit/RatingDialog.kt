package com.corporation8793.mealkit

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import androidx.fragment.app.DialogFragment
import com.corporation8793.mealkit.databinding.DialogKitScoreBinding
import com.corporation8793.mealkit.esf_wp.rest.data.Review
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_kit_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RatingDialog(val activity: Activity) : DialogFragment() {
    private var _binding: DialogKitScoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogKitScoreBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 제목, 내용 설정
//        binding.Confirm.text = title
//        binding.customTvContent.text = content
        binding.cancel.setOnClickListener {
            dismiss()
        }
        binding.confirm.setOnClickListener {
            //        like_btn.setOnClickListener {

//
                GlobalScope.launch(Dispatchers.Default) {
                    val resultOn = MainApplication.instance.boardRepository.makeReview(Review());
                    GlobalScope.launch(Dispatchers.Main) {
                        if(resultOn.first.equals("200")){
                            like_btn.isSelected = false
                        }
                    }
                }


            dismiss()
        }

        binding.rating.setOnRatingBarChangeListener { ratingBar, fl, b ->
            binding.title.setText("별점 등록 ("+fl.toInt().toString()+"/5)")
            Log.e("rating",fl.toInt().toString())
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
        params?.width = (deviceWidth * 0.88f).toInt()
        params?.height =  (size.y * 0.28f).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}