package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.BoardArticleContract
import io.defy.chicken.lover.presenter.AbstractPresenter

/**
 * Created by kim on 2017-09-14.
 */
class BoardArticleAdapterPresenter : BoardArticleContract.Presenter, AbstractPresenter<BoardArticleContract.View>() {

    private var type = "free"

    override fun attachView(view: BoardArticleContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun setType(type: String) {
        this.type = type
    }

    override fun getType(): String {
        return this.type
    }
}