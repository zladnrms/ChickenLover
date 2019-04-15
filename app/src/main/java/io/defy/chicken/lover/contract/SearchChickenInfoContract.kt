package io.defy.chicken.lover.contract

interface SearchChickenInfoContract {
    interface View {

    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}