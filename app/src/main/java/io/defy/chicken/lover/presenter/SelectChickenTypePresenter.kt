package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.SelectChickenTypeContract


class SelectChickenTypePresenter : SelectChickenTypeContract.Presenter {

    private var view: SelectChickenTypeContract.View? = null;

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as SelectChickenTypeContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}