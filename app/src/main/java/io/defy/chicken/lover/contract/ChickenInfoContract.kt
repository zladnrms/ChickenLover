package io.defy.chicken.lover.contract

import android.content.Intent

interface ChickenInfoContract {
    interface View {
        fun alertShow()

        fun alertDismiss()

        fun setImageResource(drawable: Int)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun setTypeNumber(typeNumber: Int)

        fun getTypeNumber(): Int

        fun setChickenImage()
    }
}