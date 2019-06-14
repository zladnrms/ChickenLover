package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface ChatListContract {
    interface View : BaseView {
        fun refresh()
    }

    interface Presenter : BasePresenter<View>
}