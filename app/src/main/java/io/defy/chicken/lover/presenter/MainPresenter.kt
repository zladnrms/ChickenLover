package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import io.defy.chicken.lover.contract.MainContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.ApiInterface

class MainPresenter : MainContract.Presenter, AbstractPresenter<MainContract.View>() {

    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: MainContract.View) {
        super.attachView(view)
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView() {
        super.detachView()
        this.userRepo = null
    }
}