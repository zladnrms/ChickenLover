package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.ChatListDataModel
import io.defy.chicken.lover.adapter.presenter.ChatListAdapterPresenter
import io.defy.chicken.lover.contract.ChatListContract
import io.defy.chicken.lover.model.data.ChatData
import kotlinx.android.synthetic.main.recyclerview_chat.view.*
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class ChatListAdapter(var context: Context, var lists: ArrayList<ChatData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ChatListDataModel, ChatListContract.View {

    private var presenter: ChatListAdapterPresenter

    init {
        presenter = ChatListAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_chat, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_name.text =  lists[position].name
        holder.itemView.tv_content.text =  lists[position].content
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(_list: String) {
            //itemView.tv_nickname.text = _list
        }
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun add(data: ChatData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}