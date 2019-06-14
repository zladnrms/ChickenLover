package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface BoardContract {

    interface View : BaseView {
        fun switchFragment(fragment: Fragment, tag: String)

        fun setArticleList(item : BoardArticleData)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter : BasePresenter<View> {
        fun setType(type: String)

        fun getType(): String

        fun setIndex(number: Int)

        fun getArticleList()

        fun setRecyclerViewScrollListener(list: RecyclerView)
    }
}