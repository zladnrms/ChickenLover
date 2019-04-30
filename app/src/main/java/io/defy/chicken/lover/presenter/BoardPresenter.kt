package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.BoardContract
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.network.response.BoardArticleListRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class BoardPresenter : BoardContract.Presenter {

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    private var view: BoardContract.View? = null;

    override fun attachView(view: Any) {
        this.view = view as BoardContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun getArticleList(type: String, index: Int?, limit: Int?) {
        this.view?.dialogShow()

        retrofitClient.getBoardArticleList(type, index, limit)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BoardArticleListRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: BoardArticleListRes) {
                    if(repo.result.equals("success"))
                    {
                        for(item in repo.resultArray)
                        {
                            val item_obj = JSONObject(item.toString())

                            val data = BoardArticleData(item_obj.get("_id") as String, item_obj.get("name") as String, item_obj.get("title") as String, item_obj.get("create_date") as String, item_obj.get("comment_amount") as String)
                            view?.setArticleList(data)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    view?.dialogDismiss()
                }
            })
    }
}