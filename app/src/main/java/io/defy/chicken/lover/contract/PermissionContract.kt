package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface PermissionContract {
    interface View : BaseView

    interface Presenter : BasePresenter<View>
}