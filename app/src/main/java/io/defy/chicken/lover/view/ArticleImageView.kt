package io.defy.chicken.lover.view

import android.content.Context
import android.graphics.Canvas
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.defy.chicken.lover.BuildConfig
import io.defy.chicken.lover.R
import kotlinx.android.synthetic.main.article_image_view_layout.view.*

/**
 * Created by user on 2016-12-23.
 */

class ArticleImageView : ImageView {

    init {
        this.setOnLongClickListener {
            val snackbar = Snackbar.make(this, "이 이미지를 다운로드 하시겠습니까?", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("다운로드",
                {
                        v->
                }
            )
            snackbar.show()

            true
        }
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun layout(l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setImageView(filename : String) {
        /*val ext = filename.substring(filename.lastIndexOf("."))
        if(ext.equals("gif"))
        {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .asGif()
                .load(BuildConfig.SERVER_URL + BuildConfig.SERVER_IMG_BASE_URL + filename)
                .into(iv_article_item)
        }
        else
        {*/
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .load(BuildConfig.SERVER_URL + BuildConfig.SERVER_IMG_BASE_URL + filename)
                .into(iv_article_item)
        //}
    }
}