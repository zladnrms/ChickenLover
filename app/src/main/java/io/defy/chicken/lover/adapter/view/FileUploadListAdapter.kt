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
import io.defy.chicken.lover.model.data.LocalChickenInfoData
import kotlinx.android.synthetic.main.recyclerview_img_upload.view.*
import java.io.File
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class FileUploadListAdapter(var context: Context, var lists: ArrayList<FileUploadData>) :
    RecyclerView.Adapter<FileUploadListAdapter.ViewHolder>(),
    FileUploadDataModel, FileUploadContract.View {

    private var presenter: FileUploadAdapterPresenter

    init {
        presenter = FileUploadAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recyclerview_img_upload, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(lists[position])
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: FileUploadData) {

            val file = File(data.path)
            val imageUri = Uri.fromFile(file)

            Glide.with(context).load(imageUri).into(itemView.iv_file)

            itemView.iv_file.setOnClickListener {
                lists.remove(data)
                refresh()
            }
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