package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.SearchChickenInfoListContract

/**
 * Created by kim on 2017-09-14.
 */
class SearchChickenInfoAdapterPresenter : SearchChickenInfoListContract.Presenter {

    private var view: SearchChickenInfoListContract.View? = null

    override fun attachView(view: Any) {
        this.view = view as SearchChickenInfoListContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}