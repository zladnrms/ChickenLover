package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.ChickenInfoData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView
import org.json.JSONObject
import java.util.ArrayList

interface HomeContract {
    interface View : BaseView {
        fun showChickenInfo(way: String, name: String, brand: String, thumbs_up: Int)

        fun showChickenImage(drawable: Int)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()

        fun showChickenType(item: String)
    }

    interface Presenter : BasePresenter<View> {
        var pickBrand: String?

        var pickType: String?

        fun getChickenInfo(way: String, brand: String?, type: String?)

        fun getChickenSelectHistory(mobile: String)

        fun getChickenInfo(): ChickenInfoData?
    }
}