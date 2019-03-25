package io.defy.chicken.lover.adapter.presenter

import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel
import io.defy.chicken.lover.contract.BoardArticleContract
import io.defy.chicken.lover.contract.FavoriteBrandContract
import io.defy.chicken.lover.model.FavoriteBrandRepository
import io.defy.chicken.lover.model.data.FavoriteBrandData
import io.realm.RealmResults

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