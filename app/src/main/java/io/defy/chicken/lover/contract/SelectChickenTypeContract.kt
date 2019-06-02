package io.defy.chicken.lover.contract

interface SelectChickenTypeContract {

    interface View {

    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}