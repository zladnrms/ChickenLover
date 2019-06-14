package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import io.defy.chicken.lover.contract.LoginContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.ApiInterface
import io.defy.chicken.lover.network.response.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : LoginContract.Presenter, AbstractPresenter<LoginContract.View>() {

    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: LoginContract.View) {
        super.attachView(view)
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView() {
        super.detachView()
        this.userRepo = null
    }

    override fun login(mobile : String, loginType : Int, id: String, password: String) {
        this.view?.loadingShow()

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
                    view?.loadingDismiss()
                }
            })
    }
}