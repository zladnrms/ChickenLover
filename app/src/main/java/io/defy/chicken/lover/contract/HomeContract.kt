package io.defy.chicken.lover.contract

import io.defy.chicken.lover.model.data.ChickenInfoData
import org.json.JSONObject

interface HomeContract {
    interface View {
        fun showChickenInfo(way: String, name: String, brand: String, thumbs_up: Int)

        fun showChickenImage(drawable: Int)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()

        fun showChickenType(item: String)
    }

    interface Presenter {
        var pickBrand: String?

        var pickType: String?

        fun attachView(view: Any)

        fun detachView(view: Any)

        fun getChickenInfo(way: String, brand: String?, type: String?)

        fun getChickenSelectHistory(mobile: String)

        fun getChickenInfo(): ChickenInfoData?
    }

}