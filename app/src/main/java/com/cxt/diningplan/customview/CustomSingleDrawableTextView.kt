package com.cxt.diningplan.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.cxt.diningplan.R

open class CustomSingleDrawableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var drawableWidth: Int
    private var drawableHeight: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSingleDrawableTextView)
        val top = typedArray.getDrawable(R.styleable.CustomSingleDrawableTextView_textView_drawableTop)
        val bottom = typedArray.getDrawable(R.styleable.CustomSingleDrawableTextView_textView_drawableBottom)
        val left = typedArray.getDrawable(R.styleable.CustomSingleDrawableTextView_textView_drawableStart)
        val right = typedArray.getDrawable(R.styleable.CustomSingleDrawableTextView_textView_drawableEnd)
        drawableWidth = typedArray.getDimensionPixelSize(R.styleable.CustomSingleDrawableTextView_textView_drawableWidth, 0)
        drawableHeight = typedArray.getDimensionPixelSize(R.styleable.CustomSingleDrawableTextView_textView_drawableHeight, 0)
        typedArray.recycle()
        setCompoundDrawables(left, top, right, bottom)
    }

    final override fun setCompoundDrawables(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
        super.setCompoundDrawables(
                left?.let { restrict(left) },
                top?.let { restrict(top) },
                right?.let { restrict(right) },
                bottom?.let { restrict(bottom) })
    }

    private fun restrict(drawable: Drawable): Drawable {
        drawable.setBounds(0, 0, drawableWidth, drawableHeight)
        return drawable
    }
}