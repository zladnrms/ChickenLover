package io.defy.chicken.lover.contract

import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.FavoriteBrandData

interface FavoriteBrandContract {
    interface View {
        fun refresh()

        fun setBackgroundColor(check_status : Boolean, holder : RecyclerView.ViewHolder)

        fun addFB(page : Int)
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun selectPaging(page : Int) : List<FavoriteBrandData>?

        fun checkDetect(position : Int) : Boolean?
    }
}