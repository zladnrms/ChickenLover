package io.defy.chicken.lover.contract

import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface RankNInfoContract {
    interface View : BaseView

    interface Presenter : BasePresenter<View>
}