package io.defy.chicken.lover.presenter

import android.widget.TextView
import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.HomeContract
import io.defy.chicken.lover.model.FavoriteBrandRepository
import io.defy.chicken.lover.model.data.ChickenInfoData
import io.defy.chicken.lover.model.data.SelectChickenHistoryData
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.defy.chicken.lover.network.response.ChickenSelectHistoryRes
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.net.ConnectException
import java.util.concurrent.TimeoutException


class HomePresenter : HomeContract.Presenter {

    private var chickenInfoData: ChickenInfoData? = null
    private var view: HomeContract.View? = null
    override var pickBrand: String? = null
    override var pickType: String? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as HomeContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun getChickenInfo(): ChickenInfoData? {
        return this.chickenInfoData
    }

    override fun getChickenInfo(way: String, brand: String?, type: String?) {
        retrofitClient.getChickenInfo(way, brand, type)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ChickenInfoRes> {

                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(repo: ChickenInfoRes?) {
                    repo?.let {
                        chickenInfoData = ChickenInfoData(it.id, it.way, it.name, it.brand, it.type_number, it.type_array)
                        view?.showChickenInfo(it.way, it.name, it.brand, it.thumbs_up)
                        setChickenImageByTypeNumber()
                        setChickenTypeByTypeArray()

                        pickBrand = brand
                        pickType = type
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

    private fun setChickenImageByTypeNumber() {
        this.chickenInfoData?.let {
            when (it.type_number) {
                0 -> view?.showChickenImage(R.drawable.fried)
                1 -> view?.showChickenImage(R.drawable.seasoned_fried)
                2 -> view?.showChickenImage(R.drawable.cheese_fried)
                3 -> view?.showChickenImage(R.drawable.soy_fried)
                4 -> view?.showChickenImage(R.drawable.green_onion_fried)
                5 -> view?.showChickenImage(R.drawable.garlic_fried)
                6 -> view?.showChickenImage(R.drawable.peoper_fried)
                else -> view?.showChickenImage(R.drawable.fried)
            }
        }
    }

    fun setChickenTypeByTypeArray()
    {
        this.chickenInfoData?.let {
            val obj = JSONObject( it.type_array)

            val type_array = ArrayList<String>()

            for (key: String in obj.keys()) {
                type_array.add(obj.get(key).toString())
            }

            for (item in type_array) {
                view?.showChickenType(item)
            }
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