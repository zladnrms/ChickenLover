package io.defy.chicken.lover.presenter

import io.defy.chicken.lover.contract.PermissionContract

class PermissionPresenter : PermissionContract.Presenter, AbstractPresenter<PermissionContract.View>() {
    override fun attachView(view: PermissionContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }
}