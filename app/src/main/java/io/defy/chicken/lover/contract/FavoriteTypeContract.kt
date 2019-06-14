package io.defy.chicken.lover.contract

import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.FavoriteTypeData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface FavoriteTypeContract {
    interface View : BaseView {
        fun refresh()

        fun setBackgroundColor(check_status : Boolean, holder : RecyclerView.ViewHolder)

        fun addFB(page : Int)
    }

    interface Presenter : BasePresenter<View> {
        fun selectPaging(page : Int) : List<FavoriteTypeData>?

        fun checkDetect(position : Int) : Boolean?
    }
}