package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface JoinContract {
    interface View : BaseView {
        fun complete()

        fun toastMsg(msg : String)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter : BasePresenter<View> {
        fun join(id : String, password : String, name : String)
    }
}