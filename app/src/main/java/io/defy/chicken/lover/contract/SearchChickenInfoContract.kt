package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.LocalChickenInfoData

interface SearchChickenInfoContract {
    interface View {
        fun addSearchResult(data: LocalChickenInfoData)

        fun listClear()

        fun listRefresh()

        fun dialogShow()

        fun dialogDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun initChickenInfoVersion()

        fun checkChickenInfoVersion()

        fun searchChickenInfo(p0: CharSequence?)
    }
}