package io.defy.chicken.lover.contract

import android.graphics.Bitmap
import android.support.v4.app.Fragment
import io.defy.chicken.lover.model.data.FileUploadData

interface WriteContract {
    interface View {
        fun writeResultCallback(lastId : Int)

        fun switchFragment(fragment: Fragment, tag: String)

        fun toastMsg(msg: String)

        fun dialogShow()

        fun dialogDismiss()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun write(type : String, title : String, content : String, imagesPath : ArrayList<FileUploadData>)

        fun getFileName(fileStr : String,isExtension : Boolean) : String?

        fun imgPathToBitmap(path : String) : Bitmap
    }
}