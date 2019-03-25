package io.defy.chicken.lover.contract

interface PermissionContract {
    interface View {

    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}