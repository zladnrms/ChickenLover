package io.defy.chicken.lover.contract

import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.ChatData

interface ChatContract {

    interface View {
        fun changeConnectImage(drawable : Int)

        fun getHandler(): Handler?

        fun listClear()

        fun listRefresh()

        fun listHideOn(flag: Boolean)

        fun appendChatMessage(data: ChatData)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun connect()

        fun onStop()

        fun send(content: String)

        fun onConnectClick()

        fun isConnect()
    }
}