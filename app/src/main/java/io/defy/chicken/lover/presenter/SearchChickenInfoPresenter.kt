package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.SearchChickenInfoContract

class SearchChickenInfoPresenter : SearchChickenInfoContract.Presenter {

    private var view: SearchChickenInfoContract.View? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as SearchChickenInfoContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

}