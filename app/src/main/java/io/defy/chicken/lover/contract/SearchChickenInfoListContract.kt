package io.defy.chicken.lover.contract

interface SearchChickenInfoListContract {
    interface View {
        fun refresh()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}