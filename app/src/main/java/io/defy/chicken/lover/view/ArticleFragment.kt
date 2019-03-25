package io.defy.chicken.lover.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.BoardCommentListAdapter
import io.defy.chicken.lover.contract.ArticleContract
import io.defy.chicken.lover.model.data.BoardCommentData
import io.defy.chicken.lover.network.response.BoardArticleRes
import io.defy.chicken.lover.presenter.ArticlePresenter
import io.defy.chicken.lover.rxbus.RxBus
import io.defy.chicken.lover.rxbus.CommentThumbsRefreshEvent
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.inputmethod.InputMethodManager
import org.json.JSONArray
import org.json.JSONObject
import io.defy.chicken.lover.rxbus.ArticleThumbsRefreshEvent
import kotlinx.android.synthetic.main.fragment_article.*


/**
 * Created by kim on 2017-09-20.
 */
class ArticleFragment : Fragment(), ArticleContract.View {

    private var presenter: ArticleContract.Presenter? = null
    private var adapter: BoardCommentListAdapter? = null

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private var articleId: Int? = null

    private var thumbsUpList: JSONArray? = null

    companion object {
        @JvmStatic
        fun newInstance(articleId: Int) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt("articleId", articleId)

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = activity!!.getSharedPreferences("pref", MODE_PRIVATE)
        editor = pref!!.edit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_article, container, false)

        presenter = ArticlePresenter()
        presenter?.attachView(this)

        if (arguments != null) {
            articleId = arguments?.getInt("articleId")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.getArticleInfo("free", articleId, null)

        commentList.layoutManager = LinearLayoutManager(activity)
        commentList.hasFixedSize()
        adapter = BoardCommentListAdapter((activity as BoardActivity), ArrayList<BoardCommentData>())
        commentList.adapter = adapter

        btn_submit.setOnClickListener {
            val comment = et_comment.text.toString()
            presenter?.writeBoardComment(articleId, comment)
        }

        layout_thumbs_up.setOnClickListener {
            presenter?.let {
                when(it.checkThumbsList(thumbsUpList))
                {
                    1 -> {
                        presenter?.controlArticleThumbs("free", "up", 0, articleId)
                    }
                    else ->
                    {
                        presenter?.controlArticleThumbs("free", "up", 1, articleId)
                    }
                }
            }
        }

        /* Register Event */
        RxBus.listen(CommentThumbsRefreshEvent::class.java).subscribe {
            if (it.result.equals("refresh")) {
                presenter?.getCommentList("free", pref!!.getInt("comment_id", 0))
            }
        }

        /* Register Event */
        RxBus.listen(ArticleThumbsRefreshEvent::class.java).subscribe {
            if (it.result.equals("refresh")) {
                presenter?.getArticleThumbsInfo("free", articleId, null)
            }
        }
    }

    override fun setArticleInfo(data: BoardArticleRes) {
        /* set Article Title */
        data.title?.let {
            tv_title.text = data.title
        }
        /* set Article Content */
        data.content?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv_content.setText(Html.fromHtml(data.content, Html.FROM_HTML_MODE_LEGACY))
            } else {
                tv_content.setText(Html.fromHtml(data.content))
            }
        }

        /* set Image List */
        data.img_url?.let {
            for (item in data.img_url) {
                val layout = ArticleImageViewLayout(activity as BoardActivity)
                layout.setImageView(item)
                layout_img.addView(layout)
            }
        }

        /* */
        data.thumbs?.let {
            val thumbsList = JSONObject(it)
            thumbsUpList = thumbsList.get("thumbs_up") as JSONArray

            tv_thumbs_up.text = thumbsUpList?.length().toString()
        }

        /* set comment List */
        data.comment_id?.let {
            editor?.putInt("comment_id", data.comment_id.toInt())
            editor?.commit()
            presenter?.getCommentList("free", data.comment_id.toInt())
        }
    }

    override fun setArticleThumbsInfo(data: BoardArticleRes) {
        Log.d("데이터 : ", data.thumbs.toString())
        data.thumbs?.let {
            val thumbsList = JSONObject(it)
            thumbsUpList = thumbsList.get("thumbs_up") as JSONArray

            tv_thumbs_up?.let {
                it.text = thumbsUpList?.length().toString()
            }
        }
    }

    override fun setCommentId(c_id: String) {
        editor?.putInt("comment_id", c_id.toInt())
        editor?.commit()
    }

    override fun complete() {
        activity?.let {
            try {
                val imm = it.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm!!.hideSoftInputFromWindow(it.getCurrentFocus().getWindowToken(), 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }
        }

        pref?.let {
            presenter?.getCommentList("free", it.getInt("comment_id", 0))
        }
    }

    private fun unknownPage() {
        Toast.makeText(activity, "게시물 정보를 찾을 수 없습니다", Toast.LENGTH_SHORT).show()
        (activity as BoardActivity).onBackPressed()
    }

    override fun clearCommentList() {
        adapter?.clear()
    }

    override fun setCommentList(item: BoardCommentData) {
        adapter?.add(item)
        adapter?.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        /* for memory */
        commentList.adapter = null
    }
}