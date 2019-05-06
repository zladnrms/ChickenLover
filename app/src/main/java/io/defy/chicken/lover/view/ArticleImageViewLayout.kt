package io.defy.chicken.lover.view

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
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

class ArticleImageViewLayout : LinearLayout {

    init {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.article_image_view_layout, this, true)
    }

    fun setImageView(filename : String) {
        this.setOnLongClickListener {
            val snackbar = Snackbar.make(this, "이 이미지를 다운로드 하시겠습니까?", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("다운로드",
                {
                    v-> Log.d("ㅇㅇ", "" + filename)
                }
            )

            snackbar.show()

            true
        }
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