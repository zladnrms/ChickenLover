package io.defy.chicken.lover.presenter

import io.defy.chicken.lover.view.BaseView

interface BasePresenter<in VIEW : BaseView> {
    fun attachView(view: VIEW)

    fun detachView()
}