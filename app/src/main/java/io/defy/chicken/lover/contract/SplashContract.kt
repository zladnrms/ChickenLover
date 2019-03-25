package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment

interface SplashContract {
    interface View {
        fun pass()

        fun toastMsg(msg: String)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun login()
    }
}