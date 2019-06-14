package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface ProfileContract {
    interface View : BaseView {
        fun setUserName(name : String)

        fun setUserPoint(point : Int)

        fun setUserVisitTime(visitTime : Int)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter : BasePresenter<View> {
        fun getUserName()

        fun getUserPoint()

        fun getUserVisitTime()

        fun getLoginType(): Int?
    }
}