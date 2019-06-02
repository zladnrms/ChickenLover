package io.defy.chicken.lover.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.WriteContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.model.data.FileUploadData
import io.defy.chicken.lover.network.response.WriteArticleRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.net.ConnectException
import java.util.concurrent.TimeoutException


class WritePresenter : WriteContract.Presenter {

    private var type = "free"

    private var view: WriteContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as WriteContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun setType(type: String) {
        this.type = type
    }

    override fun write(title: String, content: String, imagesPath : ArrayList<FileUploadData>) {
        this.view?.loadingShow()

        val selectVal = userRepo?.select()
        val name = selectVal?.name

        var fileList = mutableListOf<MultipartBody.Part>()

        for(item in imagesPath)
        {
            val file = File(item.path)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val filePart = MultipartBody.Part.createFormData("uploadfile[]", file.name, requestFile)
            fileList.add(filePart)
        }

        // add another part within the multipart request
        val m_type = RequestBody.create(MediaType.parse("multipart/form-data"), type)
        val m_name = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val m_title = RequestBody.create(MediaType.parse("multipart/form-data"), title)
        val m_content = RequestBody.create(MediaType.parse("multipart/form-data"), content)

        retrofitClient.writeBoardArticle(m_type, m_name, m_title, m_content, fileList)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WriteArticleRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: WriteArticleRes) {
                    when(repo.result)
                    {
                        "success"->
                        {
                            view?.loadingDismiss()
                            view?.writeResultCallback()
                        }
                        "exceed_size"->view?.toastMsg("업로드하려는 이미지 용량이 너무 큽니다")
                        "file_not_found"->view?.toastMsg("업로드하려는 이미지를 찾을 수 없습니다")
                        "not_allowed_ext"->view?.toastMsg("jpg, jpeg, png, gif 확장자만 업로드 가능합니다")
                    }
                }

                override fun onError(e: Throwable) {
                    view?.loadingDismiss()
                    when(e)
                    {
                        is ConnectException -> view?.alertShow()
                        is TimeoutException -> view?.alertShow()
                        is HttpException -> view?.alertShow()
                    }
                }

                override fun onComplete() {
                    view?.loadingDismiss()
                }
            })
    }

    override fun getFileName(fileStr : String,isExtension : Boolean) : String? {
        var fileName : String? = null
        if(isExtension)
        {
            fileName = fileStr.substring(fileStr.lastIndexOf("/"),fileStr.lastIndexOf("."));
        }else{
            fileName = fileStr.substring(fileStr.lastIndexOf("/")+1);
        }
        return fileName
    }

    override fun imgPathToBitmap(path : String) : Bitmap {
        val file = File(path)
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}