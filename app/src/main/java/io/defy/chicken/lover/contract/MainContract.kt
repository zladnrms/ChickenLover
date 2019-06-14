package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView

interface MainContract {
    interface View : BaseView {
        fun switchFragment(fragment: Fragment, tag: String, title: String)

        fun toastMsg(msg : String)
    }

    interface Presenter : BasePresenter<View>
}