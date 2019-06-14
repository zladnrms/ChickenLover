package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.FavoriteTypeData
import io.defy.chicken.lover.model.data.FileUploadData
import io.defy.chicken.lover.presenter.BasePresenter
import io.defy.chicken.lover.view.BaseView
import java.util.ArrayList

interface FileUploadContract {
    interface View : BaseView {
        fun refresh()

        fun getImagesPath() : ArrayList<FileUploadData>
    }

    interface Presenter : BasePresenter<View>
}