package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.contract.JoinContract
import io.defy.chicken.lover.contract.SplashContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.defy.chicken.lover.network.response.JoinGuestRes
import io.defy.chicken.lover.network.response.JoinMemberRes
import io.defy.chicken.lover.network.response.LoginGuestRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class JoinPresenter : JoinContract.Presenter {

    private var view: JoinContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as JoinContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun join(id: String, password: String, name: String) {
        this.view?.loadingShow()
        val selectVal = userRepo?.select()

        var hashed_value = selectVal?.hashed_value

        retrofitClient.joinAsNormal("mobile", 1, id, password, hashed_value, name)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<JoinMemberRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: JoinMemberRes) {
                    if(repo.result.equals("success")) {
                        userRepo?.update(repo.hashed_value, repo.guest_id, repo.name)
                        view?.toastMsg("가입해주셔서 감사합니다\n100 포인트가 적립됩니다")
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