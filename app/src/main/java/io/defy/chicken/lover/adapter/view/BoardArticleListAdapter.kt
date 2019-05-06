package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.BoardArticleDataModel
import io.defy.chicken.lover.adapter.presenter.BoardArticleAdapterPresenter
import io.defy.chicken.lover.contract.BoardArticleContract
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.util.DateUtil
import io.defy.chicken.lover.view.ArticleFragment
import io.defy.chicken.lover.view.BoardActivity
import io.defy.chicken.lover.view.MainActivity
import kotlinx.android.synthetic.main.recyclerview_article.view.*
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class BoardArticleListAdapter(var activity: BoardActivity, var lists: ArrayList<BoardArticleData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BoardArticleDataModel, BoardArticleContract.View {

    private var presenter: BoardArticleAdapterPresenter
    private val context: Context = activity.applicationContext

    init {
        presenter = BoardArticleAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_article, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_title.text = lists[position].title
        holder.itemView.tv_comment_amount.text = "[" + lists[position].comment_amount + "]"
        holder.itemView.tv_date.text = DateUtil.parseDate(lists[position].create_date)
        holder.itemView.tv_name.text = lists[position].name

        holder.itemView.setOnClickListener {
            activity.switchFragment(ArticleFragment.newInstance(presenter.getType(), lists[position]._id.toInt()), "article")
        }
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(_list: String) {
        }
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun add(data: BoardArticleData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }

    override fun setType(type: String) {
        presenter.setType(type)
    }
}