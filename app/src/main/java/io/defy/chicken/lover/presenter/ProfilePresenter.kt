package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ProfileContract
import io.defy.chicken.lover.model.UserInfoDataRepository

class ProfilePresenter : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as ProfileContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
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