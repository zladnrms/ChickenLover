package io.defy.chicken.lover.presenter

import io.defy.chicken.lover.view.BaseView

abstract class AbstractPresenter<VIEW : BaseView> : BasePresenter<VIEW> {
    protected var view: VIEW? = null
        private set

    override fun attachView(view: VIEW) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}