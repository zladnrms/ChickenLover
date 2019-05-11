package io.defy.chicken.lover.contract

import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.BoardCommentData
import io.defy.chicken.lover.network.response.BoardArticleRes
import org.json.JSONArray

interface ArticleContract {
    interface View {
        fun setArticleInfo(data : BoardArticleRes)

        fun setArticleThumbsInfo(data: BoardArticleRes)

        fun setCommentList(item : BoardCommentData)

        fun clearCommentList()

        fun setCommentId(c_id : String)

        fun complete()

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun setType(type: String)

        fun getType(): String

        fun setArticleId(articleId: Int)

        fun getArticle(): Int

        fun setThumbsUpList(list: JSONArray)

        fun getThumbsUpList(): JSONArray?

        fun getUserHashValue() : String?

        fun getArticleInfo(title : String?)

        fun getArticleThumbsInfo(title : String?)

        fun writeBoardComment(content : String)

        fun getCommentList(c_id: Int?)

        fun controlArticleThumbs(type2 : String, switch : Int)

        fun checkThumbsList(thumbsUpList : JSONArray?) : Int
    }
}