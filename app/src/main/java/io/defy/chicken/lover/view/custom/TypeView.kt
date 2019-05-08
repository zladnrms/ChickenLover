package io.defy.chicken.lover.view.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import io.defy.chicken.lover.R
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.view_type.view.*


/**
 * Created by user on 2016-12-23.
 */

class TypeView : TextView {



    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
            val margin = ViewGroup.MarginLayoutParams::class.java.cast(layoutParams)
            margin?.apply {
                setLayoutParams(this)
            }

    }

    constructor(context: Context, text: String) : super(context) {
        this.text = text
        this.textSize = 11F
        this.setTextColor(Color.parseColor(renderTextColor(text)))
        this.setBackgroundResource(R.drawable.round_corner_type_view)
        val drawable = this.background as GradientDrawable
        drawable.setColor(Color.parseColor(renderBackgroundcolor(text)))
    }

    private fun renderTextColor(text: String): String {
        when(text)
        {
            "어니언"-> return "#222222"
            "스노윙"-> return "#222222"
            else-> return "#FFFFFF"
        }
    }

    private fun renderBackgroundcolor(text: String): String {
        when(text)
        {
            "후라이드"-> return "#FFA811"
            "양념"-> return "#FF4848"
            "순살"-> return "#FFE08C"
            "간장"-> return "#CF6E36"
            "스모크"-> return "#CC3D3D"
            "스노윙"-> return "#FFFF8F"
            "허니"-> return "#E5D85C"
            "매운맛"-> return "#C90000"
            "파닭"-> return "#00B700"
            "어니언"-> return "#FFFFFF"
            "갈릭"-> return "#D3C64A"
            "구운"-> return "#CF6E36"
            "닭강정"-> return "#FF5E00"
            "와사비"-> return "#1DDB16"
            else-> return "#FFFFFF"
        }
    }
}