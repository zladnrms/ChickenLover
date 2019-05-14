package io.defy.chicken.lover.view

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.ChatListAdapter
import io.defy.chicken.lover.contract.ChatContract
import io.defy.chicken.lover.model.data.ChatData
import io.defy.chicken.lover.presenter.ChatPresenter
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_chat.*


/**
 * Created by kim on 2017-09-20.
 */
class ChatFragment : Fragment(), ChatContract.View {

    private var presenter : ChatContract.Presenter? = null
    private var adapter : ChatListAdapter? = null
    private var handler : Handler? = null

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        pref = context.getSharedPreferences("chat_agreement", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_chat, container, false)

        presenter = ChatPresenter()
        presenter?.attachView(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatList.layoutManager = LinearLayoutManager(activity)
        chatList.hasFixedSize()
        adapter = ChatListAdapter((activity as ChatActivity), ArrayList<ChatData>())
        chatList.adapter = adapter

        iv_door.setOnClickListener {
            presenter?.onConnectClick()
        }

        iv_send.setOnClickListener {
            val content = et_content.text.toString()

            if(!content.trim().equals(""))
                presenter?.send(content)

            et_content.text = null
        }

        btn_agree.setOnClickListener {
            layout_agreement.visibility = View.GONE
            editor?.let {
                it.putBoolean("chat_agreement", true)
                it.commit()
            }
        }
    }

    override fun listHideOn(flag: Boolean) {
        if(flag)
            layout_chatList_hider.visibility = View.VISIBLE
        else
            layout_chatList_hider.visibility = View.GONE
    }

    override fun changeConnectImage(drawable : Int){
        iv_door.setImageResource(drawable)
    }

    override fun getHandler() : Handler? {
        return handler
    }

    override fun listClear() {
        adapter?.clear()
    }

    override fun listRefresh() {
        adapter?.refresh()
    }

    override fun appendChatMessage(data: ChatData) {
        handler?.post {
            adapter?.let {
                it.add(data)
                it.refresh()
                chatList.scrollToPosition(it.itemCount - 1)
            }
        }
    }

    /* Fragment 이동 시 onPause 후 onStop */
    override fun onStop() {
        super.onStop()

        presenter?.let {
            it.onStop()
            it.isConnect()
            it.detachView(this)
        }
        presenter = null

        handler = null
    }

    /* Fragment 이동 시 onStart 부터 */
    override fun onStart() {
        super.onStart()

        presenter?.isConnect()

        if(presenter == null)
        {
            presenter = ChatPresenter()
            presenter?.attachView(this)
        }

        handler = Handler()
    }

    override fun loadingShow() {
        LoadingDialog.instance.show(activity as ChatActivity)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(this as ChatActivity, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}