package io.defy.chicken.lover.contract

interface ProfileContract {
    interface View {
        fun setUserName(name : String)

        fun setUserPoint(point : Int)

        fun setUserVisitTime(visitTime : Int)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun getUserName()

        fun getUserPoint()

        fun getUserVisitTime()

        fun getLoginType(): Int?
    }
}