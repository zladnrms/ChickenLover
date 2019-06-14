package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import io.defy.chicken.lover.contract.ProfileContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.ApiInterface

class ProfilePresenter : ProfileContract.Presenter, AbstractPresenter<ProfileContract.View>() {

    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: ProfileContract.View) {
        super.attachView(view)
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView() {
        super.detachView()
        this.userRepo = null
    }

    override fun getUserName() {
        val selectVal = userRepo?.select()
        if(selectVal == null)
        {
            Log.d("dd", "ㅇㅇㅇ")
        }
        else if(selectVal.type == 0)
        {
            view?.setUserName(selectVal.name)
        }
        else if(selectVal.type == 1)
        {
            view?.setUserName(selectVal.name)
        }
    }

    override fun getUserPoint() {

    }

    override fun getUserVisitTime() {

    }

    override fun getLoginType(): Int? {
        val selectVal = userRepo?.select()
        return selectVal?.type
    }
}