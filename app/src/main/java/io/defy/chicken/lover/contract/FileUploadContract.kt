package io.defy.chicken.lover.contract

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import io.defy.chicken.lover.model.data.FileUploadData
import java.util.ArrayList

interface FileUploadContract {
    /*
     * For FileUploadListAdapter
     */
    interface View {
        fun refresh()

        fun getImagesPath() : ArrayList<FileUploadData>
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)
    }
}