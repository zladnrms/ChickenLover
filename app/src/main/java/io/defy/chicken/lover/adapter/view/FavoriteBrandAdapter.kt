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
    RecyclerView.Adapter<FavoriteBrandAdapter.ViewHolder>(),
    FavoriteBrandDataModel, FavoriteBrandContract.View {

    private var presenter: FavoriteBrandAdapterPresenter

    init {
        presenter = FavoriteBrandAdapterPresenter().apply { attachView(this@FavoriteBrandAdapter) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.cardview_favorite_brand, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: FavoriteBrandData) {
            itemView.fb_title?.text = data.brandName

            // 체크 여부를 가져와서 그 여부에 따라 배경색을 설정한다
            val check_status: Boolean = data.checked
            setBackgroundColor(check_status, this)

            // 클릭 시 체크하며 local DB에 upgrade 및 실시간 반영
            itemView.setOnClickListener {

                val result = presenter.checkDetect(data.uid)
                result?.let {
                    data.checked = !data.checked
                }
                refresh()
            }
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