package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.FileUploadContract

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