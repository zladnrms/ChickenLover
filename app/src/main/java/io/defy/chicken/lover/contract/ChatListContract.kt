package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView

interface ChatListContract {

    interface View {
        fun refresh()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}