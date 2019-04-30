package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.BoardCommentDataModel
import io.defy.chicken.lover.adapter.presenter.BoardCommentAdapterPresenter
import io.defy.chicken.lover.contract.BoardCommentContract
import io.defy.chicken.lover.model.data.BoardCommentData
import io.defy.chicken.lover.util.DateUtil
import io.defy.chicken.lover.util.NumberUtil
import kotlinx.android.synthetic.main.recyclerview_article_comment.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class BoardCommentListAdapter(var context: Context, var lists: ArrayList<BoardCommentData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BoardCommentDataModel, BoardCommentContract.View {

    private var presenter: BoardCommentAdapterPresenter

    private val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private val editor = pref!!.edit()

    init {
        presenter = BoardCommentAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_article_comment, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_name.text =  lists[position].name
        holder.itemView.tv_date.text =  DateUtil.parseDate(lists[position].write_date)
        holder.itemView.tv_content.text =  lists[position].content
        holder.itemView.tv_thumbs_up.text = lists[position].thumbs_up.size.toString()
        holder.itemView.tv_profile.text = lists[position].name[0].toString()

        val comment_id = pref.getInt("comment_id", 0) // 해당 article의 comment_id
        val comment_c_id = lists[position]._id // 해당 article의 comment_id에 걸린 댓글 중 몇 번째 인지
        val thumbs_up_list = lists[position].thumbs_up
        var thumbs_up_status = 0 // 해당 코멘트에 thumbs_up 했는지 여부

        for(item in thumbs_up_list)
        {
            if(presenter.compareThumbsList(item))
                thumbs_up_status = 1
        }

        holder.itemView.layout_thumbs_up.setOnClickListener {
            presenter.controlCommentThumbs("free", "up", NumberUtil.valueInverse(thumbs_up_status), comment_id, comment_c_id)
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

    override fun add(data: BoardCommentData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}