package io.defy.chicken.lover.adapter.presenter

import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel
import io.defy.chicken.lover.contract.BoardArticleContract
import io.defy.chicken.lover.contract.FavoriteBrandContract
import io.defy.chicken.lover.contract.FileUploadContract
import io.defy.chicken.lover.model.FavoriteBrandRepository
import io.defy.chicken.lover.model.data.FavoriteBrandData
import io.realm.RealmResults

/**
 * Created by kim on 2017-09-14.
 */
class FileUploadAdapterPresenter : FileUploadContract.Presenter {

    private var view: FileUploadContract.View? = null

    override fun attachView(view: Any) {
        this.view = view as FileUploadContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}