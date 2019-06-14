package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface BoardCommentContract {
    interface View : BaseView {
        fun refresh()
    }

    interface Presenter : BasePresenter<View> {
        fun controlCommentThumbs(type2 : String, switch : Int, c_id : Int, c_uid : Int)

        fun compareThumbsList(uid : String) : Boolean

        fun getUid(): Int?

        fun setType(type: String)

        fun getType(): String
    }
}