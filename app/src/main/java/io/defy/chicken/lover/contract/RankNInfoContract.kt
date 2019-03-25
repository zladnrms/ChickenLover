package io.defy.chicken.lover.contract

import org.json.JSONObject

interface RankNInfoContract {
    interface View {
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }

}