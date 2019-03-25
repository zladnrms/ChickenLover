package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment

interface LoginContract {
    interface View {
        fun complete()

        fun toastMsg(msg : String)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun login(mobile : String, loginType : Int, id: String, password: String)
    }
}