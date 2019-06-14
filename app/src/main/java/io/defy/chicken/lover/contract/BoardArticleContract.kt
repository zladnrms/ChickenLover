package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface BoardArticleContract {
    interface View : BaseView {
        fun refresh()
    }

    interface Presenter : BasePresenter<View> {
        fun setType(type: String)

        fun getType(): String
    }
}