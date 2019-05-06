package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.ArticleContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.model.data.BoardCommentData
import io.defy.chicken.lover.network.response.ArticleThumbsRes
import io.defy.chicken.lover.network.response.BoardArticleRes
import io.defy.chicken.lover.network.response.BoardCommentRes
import io.defy.chicken.lover.network.response.WriteCommentRes
import io.defy.chicken.lover.rxbus.ArticleThumbsRefreshEvent
import io.defy.chicken.lover.rxbus.RxBus
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray

class ArticlePresenter : ArticleContract.Presenter {

    private var type = "free"
    private var articleId: Int = 0
    private var thumbsUpList: JSONArray? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    private var view: ArticleContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    override fun attachView(view: Any) {
        this.view = view as ArticleContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun setType(type: String) {
        this.type = type
    }

    override fun setArticleId(articleId: Int) {
        this.articleId = articleId
    }

    override fun getArticle(): Int {
        return this.articleId
    }

    override fun setThumbsUpList(list: JSONArray) {
        this.thumbsUpList = list
    }

    override fun getThumbsUpList(): JSONArray? {
        return this.thumbsUpList
    }

    override fun getUserHashValue() : String? {
        val selectVal = userRepo?.select()

        return selectVal?.hashed_value
    }

    override fun getArticleInfo(title : String?) {
        this.view?.loadingShow()

        retrofitClient.getBoardArticle(type, articleId, title)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BoardArticleRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: BoardArticleRes) {
                    view?.setArticleInfo(repo)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    view?.loadingDismiss()
                }
            })
    }

    override fun getArticleThumbsInfo(title : String?) {
        this.view?.loadingShow()

        retrofitClient.getBoardArticle(type, articleId, title)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BoardArticleRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: BoardArticleRes) {
                    view?.setArticleThumbsInfo(repo)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    view?.loadingDismiss()
                }
            })
    }

    override fun writeBoardComment(content: String) {
        this.view?.loadingShow()
        val selectVal = userRepo?.select()

        retrofitClient.writeBoardComment(articleId, selectVal?.name, content)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WriteCommentRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: WriteCommentRes) {
                    if(repo.result.equals("success"))
                    {
                        repo.comment_id?.let {
                            view?.setCommentId(repo.comment_id)
                        }
                        view?.complete()
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    view?.loadingDismiss()
                }
            })
    }

    override fun getCommentList(c_id: Int) {
        this.view?.loadingShow()
        this.view?.clearCommentList()

        retrofitClient.getBoardComment(type, c_id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BoardCommentRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: BoardCommentRes) {
                    if(repo.result.equals("success")) {
                        for(item in repo.resultArray) {
                            val data = BoardCommentData(item._id.toInt(), item.name as String, item.content as String, item.thumbs_up, item.write_date as String, item.invisible)
                            view?.setCommentList(data)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()

                }

                override fun onComplete() {
                    view?.loadingDismiss()
                }
            })
    }

    override fun controlArticleThumbs(type1 : String, type2 : String, switch : Int) {
        this.view?.loadingShow()
        val selectVal = userRepo?.select()

        retrofitClient.controlBoardArticleThumbs(type1, type2, switch, articleId, selectVal?.hashed_value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArticleThumbsRes> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(repo: ArticleThumbsRes) {
                    if(repo.result.equals("success"))
                    {
                        /*
                        * Do Nothing
                        */
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    RxBus.publish(ArticleThumbsRefreshEvent("refresh"))
                    view?.loadingDismiss()
                }
            })
    }

    override fun checkThumbsList(thumbsUpList: JSONArray?) : Int{
        val selectVal = userRepo?.select()

        for (i in 0 until thumbsUpList!!.length()) {
            val row = thumbsUpList.get(i)

            if(selectVal?.hashed_value!!.equals(row)) {
                return 1
            }
        }

        return 0
    }
}