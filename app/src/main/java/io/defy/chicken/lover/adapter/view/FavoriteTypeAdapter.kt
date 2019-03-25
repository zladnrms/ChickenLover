package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.FavoriteTypeDataModel
import io.defy.chicken.lover.adapter.presenter.FavoriteTypeAdapterPresenter
import io.defy.chicken.lover.contract.FavoriteTypeContract
import io.defy.chicken.lover.model.data.FavoriteTypeData
import kotlinx.android.synthetic.main.cardview_favorite_type.view.*
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class FavoriteTypeAdapter(var context: Context, var lists: ArrayList<FavoriteTypeData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    FavoriteTypeDataModel, FavoriteTypeContract.View {

    private var presenter: FavoriteTypeAdapterPresenter

    init {
        presenter = FavoriteTypeAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.cardview_favorite_type, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.ft_title?.text = lists[position].typeName

        /*
         * 체크 여부를 가져와서 그 여부에 따라 배경색을 설정한다
         */
        val check_status: Boolean = lists[position].checked
        setBackgroundColor(check_status, holder)

        /*
         * 클릭 시 체크하며 local DB에 upgrade 및 실시간 반영
         */
        holder.itemView.setOnClickListener {
            val result = presenter?.checkDetect(lists[position].uid)
            result?.let {
                lists[position].checked = !lists[position].checked
            }
            refresh()
        }
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(_list: String) {
            //itemView.tv_nickname.text = _list
        }
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun addFB(page: Int) {
        val data = presenter.selectPaging(page)
        data?.let {
            for (item in it) {
                add(item)
                refresh()
            }
        }
    }

    override fun setBackgroundColor(check_status: Boolean, holder: RecyclerView.ViewHolder) {
        if (check_status) {
            holder?.itemView?.ft_cardview.setCardBackgroundColor(Color.parseColor("#FFCD12"))
        } else {
            holder?.itemView?.ft_cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun add(data: FavoriteTypeData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}