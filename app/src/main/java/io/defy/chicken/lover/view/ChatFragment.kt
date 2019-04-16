package io.defy.chicken.lover.view

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.BoardArticleListAdapter
import io.defy.chicken.lover.adapter.view.ChatListAdapter
import io.defy.chicken.lover.contract.ChatContract
import io.defy.chicken.lover.model.data.ChatData
import io.defy.chicken.lover.presenter.ChatPresenter
import kotlinx.android.synthetic.main.fragment_chat.*


/**
 * Created by kim on 2017-09-20.
 */
class ChatFragment : Fragment(), ChatContract.View {

    private var presenter : ChatContract.Presenter? = null
    private var adapter : ChatListAdapter? = null
    private var handler : Handler? = null

    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
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
        }
    }

    override fun changeConnectImage(drawable : Int){
        iv_door.setImageResource(drawable)
    }

    override fun getHandler() : Handler? {
        return handler
    }

    override fun appendChatMessage(data: ChatData) {
        handler?.post {
            adapter?.add(data)
            adapter?.refresh()
        }
    }

    /* Fragment 이동 시 onPause 후 onStop */
    override fun onStop() {
        super.onStop()

        presenter?.onStop()

        presenter?.isConnect()

        presenter?.detachView(this)
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
}