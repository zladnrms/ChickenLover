package io.defy.chicken.lover.presenter

import android.util.Log
import android.widget.TextView
import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.HomeContract
import io.defy.chicken.lover.model.FavoriteBrandRepository
import io.defy.chicken.lover.model.data.SelectChickenHistoryData
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.defy.chicken.lover.network.response.ChickenSelectHistoryRes
import io.defy.chicken.lover.view.custom.TypeView
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.net.ConnectException
import java.util.concurrent.TimeoutException


class HomePresenter : HomeContract.Presenter {

    private var typeNumber: Int = 0
    private var view: HomeContract.View? = null;
    private var fbRepo: FavoriteBrandRepositoryModel? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as HomeContract.View
        this.fbRepo = FavoriteBrandRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
        this.fbRepo = null
    }

    override fun getTypeNumber(): Int {
        return this.typeNumber
    }

    override fun getChickenInfo(way: String, brand: String?, type: String?) {
        retrofitClient.getChickenInfo(way, brand, type)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ChickenInfoRes> {

                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(repo: ChickenInfoRes?) {

                    repo?.apply {
                        view?.showChickenInfo(this.way, this.name, this.brand, this.thumbs_up)

                        setChickenImageByTypeNumber(this.type_number)

                        val obj = JSONObject(this.type_array)

                        val type_array = ArrayList<String>()

                        for (key: String in obj.keys()) {
                            type_array.add(obj.get(key).toString())
                        }

                        for (item in type_array) {
                            view?.showChickenType(item)
                        }
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

    override fun getChickenSelectHistory(mobile: String) {
        retrofitClient.getChickenSelectHistory(mobile)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ChickenSelectHistoryRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: ChickenSelectHistoryRes) {
                    if (repo.result.equals("success")) {
                        for (item in repo.resultArray) {
                            val data = SelectChickenHistoryData(
                                item._id.toInt(),
                                item.name,
                                item.chicken_name,
                                item.chicken_brand,
                                item.select_date
                            )

                        }
                    }
                }

                override fun onError(e: Throwable) {
                    when (e) {
                        is ConnectException -> view?.alertShow()
                        is TimeoutException -> view?.alertShow()
                    }
                }

                override fun onComplete() {

                }
            })
    }

    private fun setChickenImageByTypeNumber(typeNumber: Int) {
        this.typeNumber = typeNumber
        when (typeNumber) {
            0 -> view?.showChickenImage(R.drawable.fried)
            1 -> view?.showChickenImage(R.drawable.seasoned_fried)
            2 -> view?.showChickenImage(R.drawable.cheese_fried)
            3 -> view?.showChickenImage(R.drawable.soy_fried)
            4 -> view?.showChickenImage(R.drawable.green_onion_fried)
            5 -> view?.showChickenImage(R.drawable.garlic_fried)
            6 -> view?.showChickenImage(R.drawable.peoper_fried)
        }
    }

    private class SimpleThread : Thread() {

        private var tv: TextView? = null
        private var flag = true

        override fun run() {
            while (flag) {

            }
            println("${Thread.currentThread()} has run.")
        }

        fun setFlag() {

        }

        fun setTextView() {

        }
    }
}