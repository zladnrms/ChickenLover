package io.defy.chicken.lover.contract

import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.ChatData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface ChatContract {
    interface View : BaseView {
        fun changeConnectImage(drawable : Int)

        fun getHandler(): Handler?

        fun listClear()

        fun listRefresh()

        fun listHideOn(flag: Boolean)

        fun appendChatMessage(data: ChatData)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter : BasePresenter<View> {
        fun connect()

        fun onStop()

        fun send(content: String)

        fun onConnectClick()

        fun isConnect()
    }
}