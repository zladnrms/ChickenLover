package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment

interface JoinContract {
    interface View {
        fun complete()

        fun toastMsg(msg : String)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun join(id : String, password : String, name : String)
    }
}