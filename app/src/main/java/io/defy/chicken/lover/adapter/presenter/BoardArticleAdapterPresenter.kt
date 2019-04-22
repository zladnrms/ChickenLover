package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.BoardArticleContract

/**
 * Created by kim on 2017-09-14.
 */
class BoardArticleAdapterPresenter : BoardArticleContract.Presenter {

    private var view: BoardArticleContract.View? = null

    override fun attachView(view: Any) {
        this.view = view as BoardArticleContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}