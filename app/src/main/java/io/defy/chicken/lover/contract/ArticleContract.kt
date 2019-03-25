package io.defy.chicken.lover.contract

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
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun getUserHashValue() : String?

        fun getArticleInfo(type : String, a_id : Int?, title : String?)

        fun getArticleThumbsInfo(type : String, a_id : Int?, title : String?)

        fun writeBoardComment(a_id : Int?, content : String)

        fun getCommentList(type: String, c_id: Int)

        fun controlArticleThumbs(type1 : String, type2 : String, switch : Int, a_id : Int?)

        fun checkThumbsList(thumbsUpList : JSONArray?) : Int
    }
}