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
                    val type_obj = JSONObject(repo.type_array)
                    renderType(repo.way, repo.name, repo.brand, type_obj)
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

    override fun renderType(way: String, name: String, brand: String, type: JSONObject) {
        view?.showChickenInfo(way, name, brand)

        var type_array = ArrayList<String>()

        for(key : String in type.keys())
        {
            type_array.add(type.get(key).toString())
        }

        for(item in type_array)
        {
            Log.d("로그 : ", item)
            when(item)
            {
                "양념"->
                {
                    view?.showChickenImage(R.drawable.seasoned_fried)
                }
                "스노윙"->
                {
                    view?.showChickenImage(R.drawable.cheese_fried)
                }
            }
        }
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