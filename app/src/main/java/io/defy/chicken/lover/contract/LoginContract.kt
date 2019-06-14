package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface LoginContract {
    interface View : BaseView {
        fun complete()

        fun toastMsg(msg : String)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter : BasePresenter<View> {
        fun login(mobile : String, loginType : Int, id: String, password: String)
    }
}