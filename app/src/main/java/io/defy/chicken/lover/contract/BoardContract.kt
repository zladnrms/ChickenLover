package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.BoardArticleData

interface BoardContract {
    interface View {
        fun switchFragment(fragment: Fragment, tag: String)

        fun setArticleList(item : BoardArticleData)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun setType(type: String)

        fun getArticleList()

        fun setRecyclerViewScrollListener(list: RecyclerView)
    }
}