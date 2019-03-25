package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.contract.JoinContract
import io.defy.chicken.lover.contract.LoginContract
import io.defy.chicken.lover.contract.SplashContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.response.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as LoginContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun login(mobile : String, loginType : Int, id: String, password: String) {
        retrofitClient.loginAsMember(mobile, loginType, id, password)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginMemberRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: LoginMemberRes) {
                    if(repo.result.equals("success")) {
                        userRepo?.update(repo.hashed_value, repo.guest_id, repo.name)
                        view?.complete()
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