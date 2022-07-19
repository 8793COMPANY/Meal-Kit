package com.corporation8793.itsofresh.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BestDecoration(
        private val height: Int)
    : RecyclerView.ItemDecoration() {




    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if(parent.getChildAdapterPosition(view) % 2 ==0){
            outRect.right = 10
        }else{
            outRect.left = 10
        }

            outRect.bottom = 25

    }


}