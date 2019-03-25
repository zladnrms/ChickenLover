package io.defy.chicken.lover.contract

interface ChickenInfoContract {
    interface View {

    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}