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
import android.support.v4.content.ContextCompat.startActivity
import android.app.ActivityOptions
import android.os.Build
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import io.defy.chicken.lover.view.ChickenInfoActivity
import io.defy.chicken.lover.view.SearchChickenInfoActivity


/**
 * Created by kim on 2017-09-16.
 */
class SearchChickenInfoListAdapter(var context: Context, var lists: ArrayList<LocalChickenInfoData>) :
    RecyclerView.Adapter<SearchChickenInfoListAdapter.ViewHolder>(),
    SearchChickenInfoListDataModel, SearchChickenInfoListContract.View {

    private var presenter: SearchChickenInfoAdapterPresenter

    init {
        presenter = SearchChickenInfoAdapterPresenter().apply { attachView(this@SearchChickenInfoListAdapter) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recyclerview_search_chicken_info, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(lists[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: LocalChickenInfoData) {
            itemView.tv_chicken_brand.text = data.brand
            itemView.tv_chicken_name.text = data.name

            when(data.type_number)
            {
                0 -> itemView.iv_chicken_img.setImageResource(R.drawable.fried)
                1 -> itemView.iv_chicken_img.setImageResource(R.drawable.seasoned_fried)
                2 -> itemView.iv_chicken_img.setImageResource(R.drawable.cheese_fried)
                3 -> itemView.iv_chicken_img.setImageResource(R.drawable.soy_fried)
                4 -> itemView.iv_chicken_img.setImageResource(R.drawable.green_onion_fried)
                5 -> itemView.iv_chicken_img.setImageResource(R.drawable.garlic_fried)
                6 -> itemView.iv_chicken_img.setImageResource(R.drawable.peoper_fried)
            }

            itemView.layout_chicken_type.removeAllViewsInLayout()
            val obj = JSONObject(data.type_array)

            val type_array = ArrayList<String>()

            for(key : String in obj.keys())
            {
                type_array.add(obj.get(key).toString())
            }

            for(item in type_array)
            {
                val tv = TypeView(context, item)
                itemView.layout_chicken_type.addView(tv)
            }

            itemView.setOnClickListener {
                val intent = Intent(context as SearchChickenInfoActivity, ChickenInfoActivity::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(context as SearchChickenInfoActivity, itemView.iv_chicken_img, "chickenImg")
                    intent.putExtra("typeNumber", data.type_number)
                    intent.putExtra("infoId", data.info_id)
                    context.startActivity(intent, options.toBundle())
                } else {
                    // makeSceneTransitionAnimation 역시 Api 21 이상에서만 동작하기 때문에 분기를 나눈다
                }
            }
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