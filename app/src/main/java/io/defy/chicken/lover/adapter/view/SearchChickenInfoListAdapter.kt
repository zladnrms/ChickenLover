package io.defy.chicken.lover.adapter.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.model.SearchChickenInfoListDataModel
import io.defy.chicken.lover.adapter.presenter.SearchChickenInfoAdapterPresenter
import io.defy.chicken.lover.contract.SearchChickenInfoListContract
import io.defy.chicken.lover.model.data.LocalChickenInfoData
import io.defy.chicken.lover.view.custom.TypeView
import kotlinx.android.synthetic.main.recyclerview_search_chicken_info.view.*
import org.json.JSONObject
import java.util.*

/**
 * Created by kim on 2017-09-16.
 */
class SearchChickenInfoListAdapter(var context: Context, var lists: ArrayList<LocalChickenInfoData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    SearchChickenInfoListDataModel, SearchChickenInfoListContract.View {

    private var presenter: SearchChickenInfoAdapterPresenter

    init {
        presenter = SearchChickenInfoAdapterPresenter()
        presenter.attachView(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.recyclerview_search_chicken_info, parent, false)

        return Item(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_chicken_brand.text = lists[position].brand
        holder.itemView.tv_chicken_name.text = lists[position].name

        when(lists[position].type_number)
        {
            0 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.fried)
            1 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.seasoned_fried)
            2 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.cheese_fried)
            3 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.soy_fried)
            4 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.green_onion_fried)
            5 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.garlic_fried)
            6 -> holder.itemView.iv_chicken_img.setImageResource(R.drawable.peoper_fried)
        }

        holder.itemView.layout_chicken_type.removeAllViewsInLayout()
        val obj = JSONObject(lists[position].type_array)

        val type_array = ArrayList<String>()

        for(key : String in obj.keys())
        {
            type_array.add(obj.get(key).toString())
        }

        for(item in type_array)
        {
            val tv = TypeView(context, item)
            holder.itemView.layout_chicken_type.addView(tv)
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

    override fun add(data: LocalChickenInfoData) {
        lists.add(data)
    }

    override fun clear() {
        lists.clear()
    }

    override fun remove(position: Int) {
        lists.removeAt(position)
    }
}