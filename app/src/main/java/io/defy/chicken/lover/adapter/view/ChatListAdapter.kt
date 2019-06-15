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
import io.defy.chicken.lover.model.data.FavoriteBrandData
import kotlinx.android.synthetic.main.recyclerview_chat.view.*
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class ChatListAdapter(var context: Context, var lists: ArrayList<ChatData>) :
    RecyclerView.Adapter<ChatListAdapter.ViewHolder>(),
    ChatListDataModel, ChatListContract.View {

    private var presenter: ChatListAdapterPresenter

    init {
        presenter = ChatListAdapterPresenter().apply { attachView(this@ChatListAdapter) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recyclerview_chat, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: ChatData) {
            itemView.tv_name.text =  data.name
            itemView.tv_content.text =  data.content
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