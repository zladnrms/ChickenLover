package io.defy.chicken.lover.contract

interface ChickenInfoContract {
    interface View {
        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}