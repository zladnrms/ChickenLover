package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface SelectFavoriteTypeContract {
    interface View : BaseView {
        fun nextPageClick()

        fun previousPageClick()
    }

    interface Presenter : BasePresenter<View> {
        fun initFirstType()

        fun nextPage()

        fun previousPage()

        fun getPage(): Int

        fun submit()
    }
}