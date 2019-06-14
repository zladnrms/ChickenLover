package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.LocalChickenInfoData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface SearchChickenInfoContract {
    interface View : BaseView {
        fun addSearchResult(data: LocalChickenInfoData)

        fun listClear()

        fun listRefresh()

        fun dialogShow()

        fun dialogDismiss()
    }

    interface Presenter : BasePresenter<View> {
        fun initChickenInfoVersion()

        fun checkChickenInfoVersion()

        fun searchChickenInfo(p0: CharSequence?)
    }
}