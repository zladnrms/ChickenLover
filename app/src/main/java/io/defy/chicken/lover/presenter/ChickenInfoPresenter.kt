package io.defy.chicken.lover.presenter

import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.model.data.ChickenInfoData
import io.defy.chicken.lover.network.ApiInterface
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.util.concurrent.TimeoutException

class ChickenInfoPresenter : ChickenInfoContract.Presenter, AbstractPresenter<ChickenInfoContract.View>() {

    private var chickenInfoData: ChickenInfoData? = null
    private var infoId: Int = 0
    private var typeNumber: Int = 0

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: ChickenInfoContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun setTypeNumber(typeNumber: Int) {
        this.typeNumber = typeNumber
    }

    override fun getTypeNumber(): Int {
        return this.typeNumber
    }

    override fun setInfoId(infoId: Int) {
        this.infoId = infoId
    }

    override fun getInfoId(): Int {
        return this.infoId
    }

    override fun setChickenImage() {
        when(this.typeNumber)
        {
            0 -> view?.setImageResource(R.drawable.fried)
            1 -> view?.setImageResource(R.drawable.seasoned_fried)
            2 -> view?.setImageResource(R.drawable.cheese_fried)
            3 -> view?.setImageResource(R.drawable.soy_fried)
            4 -> view?.setImageResource(R.drawable.green_onion_fried)
            5 -> view?.setImageResource(R.drawable.garlic_fried)
            6 -> view?.setImageResource(R.drawable.peoper_fried)
        }
    }

    override fun getChickenInfo(_id: Int?) {
        retrofitClient.getChickenInfo(_id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ChickenInfoRes> {

                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(repo: ChickenInfoRes) {
                    repo.let {
                        chickenInfoData = ChickenInfoData(it._id, it.way, it.name, it.brand, it.type_number, it.type_array)
                        view?.setChickenInfo(chickenInfoData)
                    }
                }

                override fun onError(e: Throwable) {
                    when (e) {
                        is ConnectException -> view?.alertShow()
                        is TimeoutException -> view?.alertShow()
                    }
                }
            })
    }
}