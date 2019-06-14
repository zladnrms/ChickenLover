package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.SelectChickenTypeData

interface SelectChickenTypeContract {

    interface View {

    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun getChickenTypeList(): ArrayList<SelectChickenTypeData>
    }
}