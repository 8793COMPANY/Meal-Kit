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
import com.corporation8793.itsofresh.databinding.DialogReportPostBinding
import com.corporation8793.itsofresh.esf_wp.rest.repository.Board4BaRepository
import kotlinx.android.synthetic.main.fragment_kit_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class ReportDialog(val activity: Activity, val id : String) : DialogFragment() {
    private var _binding: DialogReportPostBinding? = null
    private val binding get() = _binding!!
    var rating : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogReportPostBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 제목, 내용 설정
//        binding.Confirm.text = title
//        binding.customTvContent.text = content
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }



        binding.reportBtn.setOnClickListener {
            binding.waitingMsg.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.Default) {

                val testId = "esffnb"
                val testPw = "@esfadmin*0967"
                val basicAuth = Credentials.basic(testId, testPw)
                val boardRepository = Board4BaRepository(basicAuth)
                val updatePost = boardRepository.updatePost(id)
                println("Update Post : ${updatePost}\n")

                GlobalScope.launch(Dispatchers.Main) {
                    binding.waitingMsg.visibility = View.GONE
                    dismiss()
                    if (updatePost == "200") {
                        Toast.makeText(context, "게시물 신고가 완료되었습니다.", Toast.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                    }else{
                        Toast.makeText(context, "게시물 신고에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


        var reason = 1

        binding.swearWord.setOnCheckedChangeListener { compoundButton, b ->
            Log.e("click","checkbox1")
            if (reason != 1) {
                binding.swearWord.isChecked = true
                binding.pornography.isChecked = false
                binding.commercial.isChecked = false
                binding.plasteredPosts.isChecked = false
                binding.otherReason.isChecked = false

                binding.otherReasonBox.isEnabled = false
                binding.otherReasonBox.setText("")
            }
            reason = 1

        }

        binding.pornography.setOnCheckedChangeListener { compoundButton, b ->
            Log.e("click","checkbox2")
            if (reason != 2) {
                binding.swearWord.isChecked = false
                binding.pornography.isChecked = true
                binding.commercial.isChecked = false
                binding.plasteredPosts.isChecked = false
                binding.otherReason.isChecked = false

                binding.otherReasonBox.isEnabled = false
                binding.otherReasonBox.setText("")
            }
            reason = 2

        }

        binding.commercial.setOnCheckedChangeListener { compoundButton, b ->
            Log.e("click","checkbox3")
            if (reason != 3) {
                binding.swearWord.isChecked = false
                binding.pornography.isChecked = false
                binding.commercial.isChecked = true
                binding.plasteredPosts.isChecked = false
                binding.otherReason.isChecked = false

                binding.otherReasonBox.isEnabled = false
                binding.otherReasonBox.setText("")
            }
            reason = 3

        }

        binding.plasteredPosts.setOnCheckedChangeListener { compoundButton, b ->
            Log.e("click","checkbox4")
            if (reason != 4) {
                binding.swearWord.isChecked = false
                binding.pornography.isChecked = false
                binding.commercial.isChecked = false
                binding.plasteredPosts.isChecked = true
                binding.otherReason.isChecked = false

                binding.otherReasonBox.isEnabled = false
                binding.otherReasonBox.setText("")
            }
            reason = 4

        }

        binding.otherReason.setOnCheckedChangeListener { compoundButton, b ->
            Log.e("click","checkbox5")
            if (reason != 5) {
                binding.swearWord.isChecked = false
                binding.pornography.isChecked = false
                binding.commercial.isChecked = false
                binding.plasteredPosts.isChecked = false
                binding.otherReason.isChecked = true
                binding.otherReasonBox.isEnabled = true
            }
            reason = 5
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
        params?.width = (deviceWidth * 0.8f).toInt()
        params?.height =  (size.y * 0.5f).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}