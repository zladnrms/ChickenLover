package io.defy.chicken.lover.view.custom

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.defy.chicken.lover.BuildConfig
import io.defy.chicken.lover.R
import kotlinx.android.synthetic.main.article_image_view_layout.view.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ArticleImageViewLayout : LinearLayout {

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

    fun setImageView(filename: String) {
        Glide.with(context).applyDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
            .asBitmap()
            .load(BuildConfig.SERVER_URL + BuildConfig.SERVER_IMG_BASE_URL + filename)
            .into(iv_article_item)

        this.setOnLongClickListener {
            val snackbar = Snackbar.make(this, "이 이미지를 다운로드 하시겠습니까?", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("다운로드",
                { v ->
                    run {
                        try {
                            val envPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/ChickenLover"

                            val esp = File(envPath)

                            if (!esp.exists())
                                esp.mkdirs()

                            Log.d("★1", envPath)

                            val fos = FileOutputStream(File(esp, filename))

                            val bitmap = (iv_article_item.drawable as BitmapDrawable).bitmap

                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
                            fos.flush()
                            fos.close()

                            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(envPath + "/" + filename))))

                            Toast.makeText(context, "이미지를 저장하였습니다", Toast.LENGTH_SHORT).show()
                        } catch (e: FileNotFoundException) {
                            Toast.makeText(context, "파일을 찾을 수 없습니다", Toast.LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            Toast.makeText(context, "파일을 찾을 수 없습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
            snackbar.show()

            true
        }
    }
}