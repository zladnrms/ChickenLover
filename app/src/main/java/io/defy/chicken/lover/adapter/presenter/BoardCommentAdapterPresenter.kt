package io.defy.chicken.lover.adapter.presenter

import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.BoardCommentContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.network.response.CommentThumbsRes
import io.defy.chicken.lover.rxbus.RxBus
import io.defy.chicken.lover.rxbus.CommentThumbsRefreshEvent
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by kim on 2017-09-14.
 */
class BoardCommentAdapterPresenter : BoardCommentContract.Presenter {

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    private var type = "free"
    private var view: BoardCommentContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null

    override fun attachView(view: Any) {
        this.view = view as BoardCommentContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun getUid(): Int? {
        val selectVal = userRepo?.select()

        return selectVal?.uid
    }

    override fun compareThumbsList(data: String): Boolean {
        val selectVal = userRepo?.select()

        selectVal?.let {
            if (it.hashed_value.equals(data)) {
                return true
            }
        }
        return false
    }

    override fun controlCommentThumbs(type2: String, switch: Int, c_id: Int, c_uid: Int) {
        val selectVal = userRepo?.select()

        retrofitClient.controlBoardCommentThumbs(type, type2, switch, c_id, c_uid, selectVal?.hashed_value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CommentThumbsRes> {
                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                }

                override fun onSubscribe(d: Disposable?) {

                }

                override fun onSuccess(repo: CommentThumbsRes?) {
                    if (repo?.result.equals("success"))
                        RxBus.publish(CommentThumbsRefreshEvent("refresh"))
                }
            })
    }

    override fun getType(): String {
        return this.type
    }

    override fun setType(type: String) {
        this.type = type
    }
}