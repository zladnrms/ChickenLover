package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView

interface BoardCommentContract {
    /*
     * For BoardCommentAdapter
     */
    interface View {
        fun refresh()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun controlCommentThumbs(type2 : String, switch : Int, c_id : Int, c_uid : Int)

        fun compareThumbsList(uid : String) : Boolean

        fun getUid(): Int?

        fun setType(type: String)

        fun getType(): String
    }
}