package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.SearchChickenInfoContract
import io.defy.chicken.lover.network.response.VersionCheckRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchChickenInfoPresenter : SearchChickenInfoContract.Presenter {

    private var view: SearchChickenInfoContract.View? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as SearchChickenInfoContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun checkChickenInfoVersion() {
        retrofitClient.checkChickenInfoVersion("")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VersionCheckRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: VersionCheckRes) {
                    if(repo.code == 1)
                    {

                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                }
            })
    }
}