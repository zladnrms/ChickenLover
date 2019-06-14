package io.defy.chicken.lover.presenter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ArticleContract
import io.defy.chicken.lover.contract.BoardContract
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.network.response.BoardArticleListRes
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class BoardPresenter : BoardContract.Presenter, AbstractPresenter<BoardContract.View>() {

    private var type = "free"
    private var index = 0
    private val limit = 15

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: BoardContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun setType(type: String) {
        this.type = type
    }

    override fun getType(): String {
        return type
    }

    override fun setIndex(number: Int) {
        this.index = number
    }

    override fun getArticleList() {
        retrofitClient.getBoardArticleList(type, index, limit)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<BoardArticleListRes> {

                override fun onSubscribe(d: Disposable) {
                    view?.loadingShow()
                }

                override fun onSuccess(repo: BoardArticleListRes?) {
                    repo?.let {
                        if(it.result.equals("success"))
                        {
                            for(item in it.result_array)
                            {
                                val item_obj = JSONObject(item.toString())

                                var img_exist: String? = null

                                if(!item_obj.get("img_url").toString().equals("null"))
                                    img_exist = "exist"

                                val data = BoardArticleData(item_obj.get("_id") as String, item_obj.get("name") as String, item_obj.get("title") as String, img_exist, item_obj.get("create_date") as String, item_obj.get("comment_amount") as String)
                                view?.setArticleList(data)
                            }
                        }
                        view?.loadingDismiss()
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    override fun setRecyclerViewScrollListener(list: RecyclerView) {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager =  list.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()


                if (lastVisible >= (totalItemCount.minus(1)) && lastVisible >= index + 14) {
                    Log.d("onScrolled", "index : $index, lastVisible : $lastVisible, totalItemCount : $totalItemCount");
                    index += 15
                    getArticleList()
                }
            }
        }
        list.addOnScrollListener(scrollListener)
    }
}