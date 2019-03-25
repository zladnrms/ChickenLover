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

    private var presenterArticle: BoardArticleAdapterPresenter
    private val context: Context = activity.applicationContext

    init {
        presenterArticle = BoardArticleAdapterPresenter()
        presenterArticle.attachView(this)
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

        /*
         * 클릭 시 체크하며 local DB에 upgrade 및 실시간 반영
         */
        holder.itemView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFCD12"))
                // 버튼을 눌렀을 때
            } else if (event.action == MotionEvent.ACTION_UP) {
                // 버튼에서 손을 떼었을 때
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else if(event.action == MotionEvent.ACTION_MOVE) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            false
        }

        holder.itemView.setOnClickListener {
            activity.switchFragment(ArticleFragment.newInstance(lists[position]._id.toInt()), "article")
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
}