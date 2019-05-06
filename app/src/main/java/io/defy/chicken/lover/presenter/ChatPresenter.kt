package io.defy.chicken.lover.presenter

import android.util.Log
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.BoardContract
import io.defy.chicken.lover.contract.ChatContract
import io.defy.chicken.lover.model.UserInfoDataRepository
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.network.netty.NettyClient
import io.defy.chicken.lover.network.packet.ChatPacket
import io.defy.chicken.lover.network.packet.EntryPacket
import io.defy.chicken.lover.network.packet.ExitPacket
import io.defy.chicken.lover.network.response.BoardArticleListRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class ChatPresenter : ChatContract.Presenter {

    private var view: ChatContract.View? = null
    private var userRepo: UserInfoDataRepositoryModel? = null
    private var client : NettyClient? = null
    var connectOn = false

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as ChatContract.View
        this.userRepo = UserInfoDataRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun connect() {
        this.view?.loadingShow()

        this.client = NettyClient(view)
        val selectVal = userRepo?.select()
        val name = selectVal?.name

        this.client?.apply {
            this.isDaemon = true
            this.isFlag = true

            if (this.state === java.lang.Thread.State.NEW) {
                Log.d("connect★", "NEW")
                this.start()
            }
            else if (this.state === java.lang.Thread.State.TERMINATED) {
                Log.d("connect★", "TERMINATED")
                this.start()
            }
            else if (this.state === java.lang.Thread.State.BLOCKED) {
                Log.d("connect★", "BLOCKED")
                this.start()
            }
            else if(this.state === java.lang.Thread.State.RUNNABLE) {
                Log.d("connect★", "RUNNABLE")
                this.start()
            }
            else
            {
                Log.d("connect★", "일단왔음")
            }

            val entryPacket = EntryPacket(0, 100, name)
            client?.send(0, entryPacket)

            connectOn = true
        }

        this.view?.listHideOn(false)

        this.view?.loadingDismiss()
    }

    override fun send(content: String) {
        val selectVal = userRepo?.select()
        val name = selectVal?.name

        val chatPacket = ChatPacket(2, 100, name, content, "2000")
        this.client?.send(2, chatPacket)
    }

    override fun onStop() {
        val selectVal = userRepo?.select()
        val name = selectVal?.name

        val exitPacket = ExitPacket(1, 100, name)
        this.client?.send(1, exitPacket)

        /* Activity's onDestroy */
        this.client?.apply {
            this.isFlag = false
            if (this.state === java.lang.Thread.State.TERMINATED) {
                this.interrupt()
            }
        }
        this.client = null

        this.connectOn = false

        this.view?.listClear()
        this.view?.listRefresh()

        this.view?.listHideOn(true)
    }

    override fun onConnectClick() {
        if (this.connectOn) {
            this.view?.changeConnectImage(R.drawable.ic_door_enter)
            this.onStop()
        } else {
            this.view?.changeConnectImage(R.drawable.ic_door_exit)
            this.connect()
        }
    }

    override fun isConnect() {
        if (this.connectOn) {
            this.view?.changeConnectImage(R.drawable.ic_door_exit)
        } else {
            this.view?.changeConnectImage(R.drawable.ic_door_enter)
        }
    }
}