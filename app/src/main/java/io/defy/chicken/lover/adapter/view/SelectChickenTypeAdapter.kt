package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import java.util.*
import io.defy.chicken.lover.adapter.model.SelectChickenTypeDataModel
import io.defy.chicken.lover.contract.SelectChickenTypeContract
import io.defy.chicken.lover.model.data.SelectChickenTypeData
import io.defy.chicken.lover.presenter.SelectChickenTypePresenter
import kotlinx.android.synthetic.main.recyclerview_select_chicken_type.view.*
import kotlinx.android.synthetic.main.recyclerview_search_chicken_info.view.iv_chicken_img


/**
 * Created by kim on 2017-09-16.
 */
class SelectChickenTypeAdapter(var context: Context, var lists: ArrayList<SelectChickenTypeData>) :
    RecyclerView.Adapter<SelectChickenTypeAdapter.ViewHolder>(),
    SelectChickenTypeDataModel, SelectChickenTypeContract.View {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recyclerview_select_chicken_type, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(lists[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: SelectChickenTypeData) {
            when(data.typeNumber)
            {
                0 -> itemView.iv_chicken_img.setImageResource(R.drawable.fried)
                1 -> itemView.iv_chicken_img.setImageResource(R.drawable.seasoned_fried)
                2 -> itemView.iv_chicken_img.setImageResource(R.drawable.cheese_fried)
                3 -> itemView.iv_chicken_img.setImageResource(R.drawable.soy_fried)
                4 -> itemView.iv_chicken_img.setImageResource(R.drawable.green_onion_fried)
                5 -> itemView.iv_chicken_img.setImageResource(R.drawable.garlic_fried)
                6 -> itemView.iv_chicken_img.setImageResource(R.drawable.peoper_fried)
            }

            itemView.tv_type.text = data.name
        }
    }

    override fun add(data: SelectChickenTypeData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}