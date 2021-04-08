package com.tuandi.architecture.example.ui.fragments.list

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.scwang.smart.drawable.ProgressDrawable
import com.scwang.smart.refresh.classics.ClassicsAbstract
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.classics.R
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.util.SmartUtil


class MyClassicsHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ClassicsAbstract<ClassicsHeader?>(context, attrs, 0), RefreshHeader {
    init {
        inflate(context, com.tuandi.architecture.R.layout.classics_header, this)
        val thisView: View = this
        mProgressView = thisView.findViewById(R.id.srl_classics_progress)
        val progressView: View = mProgressView
        mArrowView = thisView.findViewById(R.id.srl_classics_arrow)
        val arrowView: View = mArrowView
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader)
        val lpProgress = progressView.layoutParams as LayoutParams
        val lpUpdateText = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lpUpdateText.topMargin = ta.getDimensionPixelSize(
            R.styleable.ClassicsHeader_srlTextTimeMarginTop,
            SmartUtil.dp2px(0f)
        )
        lpProgress.rightMargin = ta.getDimensionPixelSize(
            R.styleable.ClassicsHeader_srlDrawableMarginRight,
            SmartUtil.dp2px(20f)
        )
        lpProgress.width = ta.getLayoutDimension(
            R.styleable.ClassicsHeader_srlDrawableProgressSize,
            lpProgress.width
        )
        lpProgress.height = ta.getLayoutDimension(
            R.styleable.ClassicsHeader_srlDrawableProgressSize,
            lpProgress.height
        )
        lpProgress.width =
            ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.width)
        lpProgress.height =
            ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.height)
        mFinishDuration = ta.getInt(R.styleable.ClassicsHeader_srlFinishDuration, mFinishDuration)
        mSpinnerStyle = SpinnerStyle.values[ta.getInt(
            R.styleable.ClassicsHeader_srlClassicsSpinnerStyle,
            mSpinnerStyle.ordinal
        )]
        if (ta.hasValue(R.styleable.ClassicsHeader_srlDrawableProgress)) {
            mProgressView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsHeader_srlDrawableProgress))
        } else if (mProgressView.drawable == null) {
            mProgressDrawable = ProgressDrawable()
            mProgressView.setImageDrawable(mProgressDrawable)
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlPrimaryColor)) {
            super.setPrimaryColor(ta.getColor(R.styleable.ClassicsHeader_srlPrimaryColor, 0))
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsHeader_srlAccentColor, 0))
        }
        ta.recycle()
        progressView.animate().interpolator = null
        if (!thisView.isInEditMode) {
            progressView.visibility = GONE
        }
    }
}