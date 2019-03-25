package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.FavoriteBrandDataModel
import io.defy.chicken.lover.adapter.presenter.FavoriteBrandAdapterPresenter
import io.defy.chicken.lover.contract.FavoriteBrandContract
import io.defy.chicken.lover.model.data.FavoriteBrandData
import kotlinx.android.synthetic.main.cardview_favorite_brand.view.*
import java.text.SimpleDateFormat
import java.text.ParseException
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class FavoriteBrandAdapter(var context: Context, var lists: ArrayList<FavoriteBrandData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    FavoriteBrandDataModel, FavoriteBrandContract.View {

    private var presenter: FavoriteBrandAdapterPresenter

    init {
        presenter = FavoriteBrandAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.cardview_favorite_brand, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.fb_title?.text = lists[position].brandName

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
            holder?.itemView?.fb_cardview.setCardBackgroundColor(Color.parseColor("#FFCD12"))
        } else {
            holder?.itemView?.fb_cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun add(data: FavoriteBrandData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}