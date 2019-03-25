package io.defy.chicken.lover.contract

import org.json.JSONObject

interface HomeContract {
    interface View {
        fun showChickenInfo(way: String, name: String, brand: String, type: JSONObject)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun getChickenInfo(way: String, brand: String?, type: String?)

        fun getChickenSelectHistory(mobile: String)
    }

}