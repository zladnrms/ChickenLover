package io.defy.chicken.lover.contract

interface SearchChickenInfoContract {
    interface View {

    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun initChickenInfoVersion()

        fun checkChickenInfoVersion()

        fun searchChickenInfo(p0: CharSequence?)
    }
}