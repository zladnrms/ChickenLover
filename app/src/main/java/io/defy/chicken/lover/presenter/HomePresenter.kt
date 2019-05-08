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
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.net.ConnectException
import java.util.concurrent.TimeoutException


class HomePresenter : HomeContract.Presenter {

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
    }

    override fun getChickenInfo(way: String, brand: String?, type: String?) {
        retrofitClient.getChickenInfo(way, brand, type)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ChickenInfoRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: ChickenInfoRes) {
                    view?.showChickenInfo(repo.way, repo.name, repo.brand, repo.thumbs_up)

                    when(repo.type_number)
                    {
                        0 -> view?.showChickenImage(R.drawable.fried)
                        1 -> view?.showChickenImage(R.drawable.seasoned_fried)
                        2 -> view?.showChickenImage(R.drawable.cheese_fried)
                        3 -> view?.showChickenImage(R.drawable.soy_fried)
                        4 -> view?.showChickenImage(R.drawable.green_onion_fried)
                        5 -> view?.showChickenImage(R.drawable.garlic_fried)
                        6 -> view?.showChickenImage(R.drawable.peoper_fried)
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

    override fun getChickenSelectHistory(mobile: String) {
        retrofitClient.getChickenSelectHistory(mobile)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ChickenSelectHistoryRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: ChickenSelectHistoryRes) {
                    if(repo.result.equals("success"))
                    {
                        for(item in repo.resultArray) {
                            val data = SelectChickenHistoryData(item._id.toInt(), item.name, item.chicken_name, item.chicken_brand, item.select_date)

                        }
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

    private class SimpleThread: Thread() {

        private var tv : TextView? = null
        private var flag = true

        override fun run() {
            while(flag)
            {

            }
            println("${Thread.currentThread()} has run.")
        }

        fun setFlag() {

        }

        fun setTextView() {

        }
    }
}