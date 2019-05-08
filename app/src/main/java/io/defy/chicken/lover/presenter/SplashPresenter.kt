package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.contract.SplashContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.defy.chicken.lover.network.response.JoinGuestRes
import io.defy.chicken.lover.network.response.LoginGuestRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.util.concurrent.TimeoutException

class SplashPresenter : SplashContract.Presenter {

    private var view: SplashContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as SplashContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun login() {

        val selectVal = userRepo?.select()

        /*
         * GUEST LOGIN
         */
        if (selectVal?.guestId != null) {
            loginAsGuest("mobile", selectVal.hashed_value)
        } else if (selectVal?.name != null) {
            loginAsAuto("mobile", 1, selectVal.hashed_value)
        } else {
            joinAsGuest()
        }
    }

    private fun loginAsGuest(mobile: String, hashed_value: String?) {
        retrofitClient.loginAsGuest(mobile, hashed_value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginGuestRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: LoginGuestRes) {
                    if (repo.result.equals("success")) {
                        view?.pass()
                    } else {
                        joinAsGuest()
                    }
                }

                override fun onError(e: Throwable) {
                    when(e)
                    {
                        is ConnectException -> view?.alertShow()
                        is TimeoutException -> view?.alertShow()
                    }
                }

                override fun onComplete() {
                }
            })
    }

    private fun joinAsGuest() {
        retrofitClient.joinAsGuest("post")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<JoinGuestRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: JoinGuestRes) {
                    if (repo.result.equals("success")) {
                        userRepo?.insert(0, 0, repo.hashed_value, repo.guest_id, repo.name, null, null)
                        view?.pass()
                    } else {
                        view?.alertShow()
                    }
                }

                override fun onError(e: Throwable) {
                    when(e)
                    {
                        is ConnectException -> view?.alertShow()
                        is TimeoutException -> view?.alertShow()
                    }
                }

                override fun onComplete() {
                }
            })
    }

    private fun loginAsAuto(mobile: String, loginType : Int, hashed_value: String?) {
        retrofitClient.loginAsAuto(mobile, loginType, hashed_value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginGuestRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: LoginGuestRes) {
                    if (repo.result.equals("success")) {
                        view?.pass()
                    }
                }

                override fun onError(e: Throwable) {
                    when(e)
                    {
                        is ConnectException -> view?.alertShow()
                        is TimeoutException -> view?.alertShow()
                    }
                }

                override fun onComplete() {
                }
            })
    }
}