package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import io.defy.chicken.lover.view.*
import kotlinx.android.synthetic.main.recyclerview_article.view.*
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class BoardArticleListAdapter(var activity: MainActivity, var lists: ArrayList<BoardArticleData>) :
    RecyclerView.Adapter<BoardArticleListAdapter.ViewHolder>(),
    BoardArticleDataModel, BoardArticleContract.View {

    private var presenter: BoardArticleAdapterPresenter
    private val context: Context = activity.applicationContext

    init {
        presenter = BoardArticleAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_article, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: BoardArticleData) {
            itemView.tv_title.text = data.title
            itemView.tv_comment_amount.text = "[" + data.comment_amount + "]"
            itemView.tv_date.text = DateUtil.parseDate(data.create_date)
            itemView.tv_name.text = data.name

            data.img_exist?.apply {
                itemView.iv_image.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                val intent = Intent(context, ArticleActivity::class.java)
                intent.putExtra("type", presenter.getType())
                intent.putExtra("id", data._id.toInt())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                //activity.switchFragment(ArticleFragment.newInstance(presenter.getType(), data._id.toInt()), "article")
            }
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