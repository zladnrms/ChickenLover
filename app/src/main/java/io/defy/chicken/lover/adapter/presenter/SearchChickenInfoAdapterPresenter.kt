package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.FileUploadContract
import io.defy.chicken.lover.contract.SearchChickenInfoListContract
import io.defy.chicken.lover.presenter.AbstractPresenter
import kotlinx.android.synthetic.main.recyclerview_search_chicken_info.view.*

/**
 * Created by kim on 2017-09-14.
 */
class SearchChickenInfoAdapterPresenter : SearchChickenInfoListContract.Presenter, AbstractPresenter<SearchChickenInfoListContract.View>() {
    override fun attachView(view: SearchChickenInfoListContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }
}