package io.defy.chicken.lover.presenter

import io.defy.chicken.lover.contract.RankNInfoContract
import io.defy.chicken.lover.network.ApiInterface


class RankNInfoPresenter : RankNInfoContract.Presenter, AbstractPresenter<RankNInfoContract.View>() {

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: RankNInfoContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }
}