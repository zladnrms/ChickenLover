package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.ChatListContract

/**
 * Created by kim on 2017-09-14.
 */
class ChatListAdapterPresenter : ChatListContract.Presenter {

    private var view: ChatListContract.View? = null

    override fun attachView(view: Any) {
        this.view = view as ChatListContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}