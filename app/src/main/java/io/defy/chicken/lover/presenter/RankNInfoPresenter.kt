package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.RankNInfoContract


class RankNInfoPresenter : RankNInfoContract.Presenter {

    private var view: RankNInfoContract.View? = null;

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as RankNInfoContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}