package io.defy.chicken.lover.contract

import android.content.Intent
import io.defy.chicken.lover.model.data.ChickenInfoData

interface ChickenInfoContract {
    interface View {
        fun alertShow()

        fun alertDismiss()

        fun setImageResource(drawable: Int)

        fun setChickenInfo(chickenInfoData: ChickenInfoData?)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun setTypeNumber(typeNumber: Int)

        fun getTypeNumber(): Int

        fun setInfoId(infoId: Int)

        fun getInfoId(): Int

        fun setChickenImage()

        fun getChickenInfo(_id: Int?)
    }
}