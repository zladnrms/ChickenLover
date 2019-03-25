package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChickenInfoPresenter : ChickenInfoContract.Presenter {

    private var view: ChickenInfoContract.View? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as ChickenInfoContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

}