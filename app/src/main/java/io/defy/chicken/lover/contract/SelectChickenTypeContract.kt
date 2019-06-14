package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.SelectChickenTypeData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface SelectChickenTypeContract {
    interface View : BaseView {
    }

    interface Presenter : BasePresenter<View> {
        fun getChickenTypeList(): ArrayList<SelectChickenTypeData>
    }
}