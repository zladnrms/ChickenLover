package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.BoardCommentData
import io.defy.chicken.lover.network.response.BoardArticleRes
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView
import org.json.JSONArray

interface ArticleContract {
    interface View : BaseView {
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

    interface Presenter : BasePresenter<View> {
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