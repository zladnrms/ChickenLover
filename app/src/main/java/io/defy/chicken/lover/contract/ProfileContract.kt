package io.defy.chicken.lover.contract

interface ProfileContract {
    interface View {
        fun setUserName(name : String)

        fun setUserPoint(point : Int)

        fun setUserVisitTime(visitTime : Int)
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