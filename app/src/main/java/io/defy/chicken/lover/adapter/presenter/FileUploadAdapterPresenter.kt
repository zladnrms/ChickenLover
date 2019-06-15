package io.defy.chicken.lover.adapter.presenter

import io.defy.chicken.lover.contract.FileUploadContract
import io.defy.chicken.lover.presenter.AbstractPresenter

/**
 * Created by kim on 2017-09-14.
 */
class FileUploadAdapterPresenter : FileUploadContract.Presenter, AbstractPresenter<FileUploadContract.View>() {
    override fun attachView(view: FileUploadContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }
}