package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.SearchChickenInfoListDataModel
import io.defy.chicken.lover.adapter.presenter.SearchChickenInfoAdapterPresenter
import io.defy.chicken.lover.contract.SearchChickenInfoListContract
import io.defy.chicken.lover.model.data.LocalChickenInfoData
import kotlinx.android.synthetic.main.recyclerview_search_chicken_info.view.*
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class SearchChickenInfoListAdapter(var context: Context, var lists: ArrayList<LocalChickenInfoData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    SearchChickenInfoListDataModel, SearchChickenInfoListContract.View {

    private var presenter: SearchChickenInfoAdapterPresenter

    init {
        presenter = SearchChickenInfoAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_search_chicken_info, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_chicken_info.text = lists[position].brand + " => " + lists[position].name
        //holder.itemView.tv_star.text =  lists[position].name

    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(_list: String) {
            //itemView.tv_nickname.text = _list
        }
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun add(data: LocalChickenInfoData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}