package io.defy.chicken.lover.contract

import android.graphics.Bitmap
import android.support.v4.app.Fragment
import io.defy.chicken.lover.model.data.FileUploadData

interface WriteContract {
    interface View {
        fun writeResultCallback()

        //fun switchFragment(fragment: Fragment, tag: String)

        fun toastMsg(msg: String)

        fun loadingShow()

        fun loadingDismiss()

        fun alertShow()

        fun alertDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun setType(type: String)

        fun write(title : String, content : String, imagesPath : ArrayList<FileUploadData>)

        fun getFileName(fileStr : String,isExtension : Boolean) : String?

        fun imgPathToBitmap(path : String) : Bitmap
    }
}