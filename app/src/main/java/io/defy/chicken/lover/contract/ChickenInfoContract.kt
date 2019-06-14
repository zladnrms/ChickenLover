package io.defy.chicken.lover.contract

import android.content.Intent
import io.defy.chicken.lover.model.data.ChickenInfoData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface ChickenInfoContract {
    interface View : BaseView {
        fun alertShow()

        fun alertDismiss()

        fun setImageResource(drawable: Int)

        fun setChickenInfo(chickenInfoData: ChickenInfoData?)
    }

    interface Presenter : BasePresenter<View> {
        fun setTypeNumber(typeNumber: Int)

        fun getTypeNumber(): Int

        fun setInfoId(infoId: Int)

        fun getInfoId(): Int

        fun setChickenImage()

        fun getChickenInfo(_id: Int?)
    }
}