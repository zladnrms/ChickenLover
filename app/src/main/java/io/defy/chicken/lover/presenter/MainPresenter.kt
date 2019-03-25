package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.MainContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.response.JoinGuestRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null;
    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as MainContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }
}