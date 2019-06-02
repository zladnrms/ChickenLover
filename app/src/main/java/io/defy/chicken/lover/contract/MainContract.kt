package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment

interface MainContract {
    interface View {
        fun switchFragment(fragment: Fragment, tag: String, title: String)

        fun toastMsg(msg : String)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}