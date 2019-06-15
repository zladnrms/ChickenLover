package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.ChatListContract
import io.defy.chicken.lover.presenter.AbstractPresenter

/**
 * Created by kim on 2017-09-14.
 */
class ChatListAdapterPresenter : ChatListContract.Presenter, AbstractPresenter<ChatListContract.View>() {
    override fun attachView(view: ChatListContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }
}