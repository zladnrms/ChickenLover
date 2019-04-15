package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.FileUploadDataModel
import io.defy.chicken.lover.adapter.presenter.FileUploadAdapterPresenter
import io.defy.chicken.lover.contract.FileUploadContract
import io.defy.chicken.lover.model.data.FileUploadData
import kotlinx.android.synthetic.main.recyclerview_img_upload.view.*
import java.io.File
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class FileUploadListAdapter(var context: Context, var lists: ArrayList<FileUploadData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    FileUploadDataModel, FileUploadContract.View {

    private var presenter: FileUploadAdapterPresenter

    init {
        presenter = FileUploadAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_img_upload, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.itemView.iv_file.setImageBitmap(lists[position].bitmap)
        val file = File(lists[position].path)
        val imageUri = Uri.fromFile(file)

        Glide.with(context)
            .load(imageUri)
            .into(holder.itemView.iv_file)

        holder.itemView.iv_file.setOnClickListener {
            lists.remove(lists[position])
            refresh()
        }
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(_list: String) {
            //itemView.tv_nickname.text = _list
        }
    }

    override fun getImagesPath() : ArrayList<FileUploadData> {
        return lists;
    }

    override fun refresh() {
        notifyDataSetChanged()
    }

    override fun add(data: FileUploadData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}