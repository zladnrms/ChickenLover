package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.AppVersionDataModel
import com.zeniex.www.zeniexautomarketing.model.LocalChickenInfoModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.BuildConfig
import io.defy.chicken.lover.contract.SearchChickenInfoContract
import io.defy.chicken.lover.model.AppVersionDataRepository
import io.defy.chicken.lover.model.LocalChickenInfoRepository
import io.defy.chicken.lover.model.data.LocalChickenInfoData
import io.defy.chicken.lover.network.response.UpdateLocalChickenInfoRes
import io.defy.chicken.lover.network.response.VersionCheckRes
import io.defy.chicken.lover.view.CustomDialog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.*

class SearchChickenInfoPresenter : SearchChickenInfoContract.Presenter {

    private var view: SearchChickenInfoContract.View? = null
    private var localRepo: LocalChickenInfoModel? = null
    private var appVersionRepo: AppVersionDataModel? = null
    private var dialog: CustomDialog? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as SearchChickenInfoContract.View
        this.localRepo = LocalChickenInfoRepository.getInstance()
        this.appVersionRepo = AppVersionDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun initChickenInfoVersion() {
        this.appVersionRepo?.apply {
            if(this.selectVersionCode() == 0)
               this.insert(1)
        }
    }

    override fun checkChickenInfoVersion() {
        var updateOk = false
        var updateVersionCode = 0
        val localVersionCode = this.appVersionRepo?.selectVersionCode()

        retrofitClient.checkChickenInfoVersion("")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VersionCheckRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: VersionCheckRes) {
                    if (repo.code != localVersionCode) {
                        updateOk = true
                        updateVersionCode = repo.code
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    if (updateOk)
                        updateLocalChickenInfoForSearch(updateVersionCode)
                }
            })
    }

    private fun updateLocalChickenInfoForSearch(updateVersionCode: Int) {
        this.localRepo?.delete()

        retrofitClient.updateLocalChickenInfo()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UpdateLocalChickenInfoRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: UpdateLocalChickenInfoRes) {
                    if (repo.result.equals("success")) {
                        localRepo?.let {
                            it.delete()

                            if(it.selectAll().isNotEmpty())
                            {
                                Log.d("onNExt", "비움")
                                return
                            }

                            for (item in repo.resultArray) {
                                val item_obj = JSONObject(item.toString())

                                it.insert(
                                    item_obj.get("brand") as String,
                                    item_obj.get("name") as String
                                )
                            }

                            appVersionRepo?.update(updateVersionCode)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {

                }
            })
    }

    override fun searchChickenInfo(text: CharSequence?) {
        this.view?.dialogShow()

        view?.apply {
            this.listClear()

            val searchResultList = localRepo?.select(text.toString())

            searchResultList?.let {
                val iterator = it.iterator()
                while(iterator.hasNext())
                {
                    this.addSearchResult(iterator.next())
                }
            }

            this.listRefresh()
        }

        this.view?.dialogDismiss()
    }
}