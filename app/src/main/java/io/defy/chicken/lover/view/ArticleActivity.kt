package io.defy.chicken.lover.view

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.widget.Toast
import io.defy.chicken.lover.adapter.view.BoardCommentListAdapter
import io.defy.chicken.lover.contract.ArticleContract
import io.defy.chicken.lover.model.data.BoardCommentData
import io.defy.chicken.lover.network.response.BoardArticleRes
import io.defy.chicken.lover.presenter.ArticlePresenter
import io.defy.chicken.lover.rxbus.RxBus
import io.defy.chicken.lover.rxbus.CommentThumbsRefreshEvent
import android.view.inputmethod.InputMethodManager
import io.defy.chicken.lover.R
import org.json.JSONArray
import org.json.JSONObject
import io.defy.chicken.lover.rxbus.ArticleThumbsRefreshEvent
import io.defy.chicken.lover.view.custom.ArticleImageViewLayout
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_article.*




/**
 * Created by kim on 2017-09-20.
 */
class ArticleActivity : BaseActivity(), ArticleContract.View {

    private lateinit var adapter : BoardCommentListAdapter
    private val presenter: ArticleContract.Presenter by lazy {
        ArticlePresenter().apply { attachView(this@ArticleActivity) }
    }
    private val pref: SharedPreferences by lazy {
        getSharedPreferences("pref", MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        pref.edit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        setDataFromIntent()

        adapter = BoardCommentListAdapter(this, ArrayList<BoardCommentData>()).apply { setType(presenter.getType()) }
        commentList.apply {
            this.layoutManager = LinearLayoutManager(this@ArticleActivity)
            this.hasFixedSize()
        }
        commentList.adapter = adapter

        iv_submit.setOnClickListener {
            val comment = et_comment.text.toString()

            if(comment.trim().equals(""))
            {
                Toast.makeText(this, "내용을 작성해주세요", Toast.LENGTH_SHORT).show()
            }
            else
            {
                et_comment.text = null
                presenter.writeBoardComment(comment)
            }
        }

        layout_thumbs_up.setOnClickListener {
            presenter.let {
                val thumbsUpList = presenter.getThumbsUpList()

                when(it.checkThumbsList(thumbsUpList))
                {
                    1 -> {
                        presenter.controlArticleThumbs("up", 0)
                    }
                    else ->
                    {
                        presenter.controlArticleThumbs("up", 1)
                    }
                }
            }
        }

        /* Register Event */
        RxBus.listen(CommentThumbsRefreshEvent::class.java).subscribe {
            if (it.result.equals("refresh")) {
                presenter.getCommentList(pref.getInt("comment_id", 0))
            }
        }

        /* Register Event */
        RxBus.listen(ArticleThumbsRefreshEvent::class.java).subscribe {
            if (it.result.equals("refresh")) {
                presenter.getArticleThumbsInfo(null)
            }
        }

        presenter.getArticleInfo(null)
    }

    private fun setDataFromIntent() {
        val intent = intent
        presenter.apply {
            setType(intent.getStringExtra("type"))
            setArticleId(intent.getIntExtra("id", 0))
        }
    }

    override fun setArticleInfo(data: BoardArticleRes) {
        data.apply {
            title.let {
                tv_title.text = it
            }
            writer.let {
                tv_writer.text = it
                tv_profile.text = it[0].toString()
            }
            content.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_content.setText(Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY))
                } else {
                    tv_content.setText(Html.fromHtml(it))
                }
            }
            img_url?.let{
                for (item in img_url) {
                    val layout = ArticleImageViewLayout(applicationContext)
                    layout.setImageView(item)
                    layout_img.addView(layout)
                }
            }
            thumbs.let{
                val thumbsList = JSONObject(it)
                val thumbsUpList = thumbsList.get("thumbs_up") as JSONArray
                presenter?.setThumbsUpList(thumbsUpList)

                tv_thumbs_up.text = thumbsUpList?.length().toString()
            }
            comment_id.let {
                editor.putInt("comment_id", it.toInt())
                editor.commit()
                presenter.getCommentList(it.toInt())
            }
        }
    }

    override fun setArticleThumbsInfo(data: BoardArticleRes) {
        data.thumbs.let {
            val thumbsList = JSONObject(it)
            val thumbsUpList = thumbsList.get("thumbs_up") as JSONArray
            presenter.setThumbsUpList(thumbsUpList)

            tv_thumbs_up.text = thumbsUpList.length().toString()
        }
    }

    override fun setCommentId(c_id: String) {
        editor.putInt("comment_id", c_id.toInt())
        editor.commit()
    }

    override fun complete() {
        this.apply {
            try {
                val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }
        }

        pref.apply {
            presenter.getCommentList(this.getInt("comment_id", 0))
        }
    }

    override fun loadingShow() {
        LoadingDialog.instance.show(this)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(this, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun clearCommentList() {
        adapter.clear()
    }

    override fun setCommentList(item: BoardCommentData) {
        adapter.add(item)
        adapter.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }

    override fun onLowMemory() {
        super.onLowMemory()

        layout_img.removeAllViewsInLayout()
        Toast.makeText(this, "메모리가 부족하여 이미지를 보여주지 않습니다", Toast.LENGTH_SHORT).show()
    }
}