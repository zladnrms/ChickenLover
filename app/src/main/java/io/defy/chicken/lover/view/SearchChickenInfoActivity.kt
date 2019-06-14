package io.defy.chicken.lover.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.SearchChickenInfoListAdapter
import io.defy.chicken.lover.contract.SearchChickenInfoContract
import io.defy.chicken.lover.model.data.LocalChickenInfoData
import io.defy.chicken.lover.presenter.SearchChickenInfoPresenter
import kotlinx.android.synthetic.main.activity_search_chicken_info.*
import android.support.v7.widget.DividerItemDecoration
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.recyclerview_search_chicken_info.view.*
import android.support.v4.app.ActivityOptionsCompat
import android.view.LayoutInflater


class SearchChickenInfoActivity : BaseActivity(), SearchChickenInfoContract.View {

    private lateinit var adapter : SearchChickenInfoListAdapter
    private val presenter: SearchChickenInfoContract.Presenter by lazy {
        SearchChickenInfoPresenter().apply { attachView(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_chicken_info)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        adapter = SearchChickenInfoListAdapter(this, ArrayList())
        searchList.apply {
            this.layoutManager = LinearLayoutManager(this@SearchChickenInfoActivity)
            this.hasFixedSize()
            this.addItemDecoration(DividerItemDecoration(this@SearchChickenInfoActivity, DividerItemDecoration.VERTICAL))
        }
        searchList.adapter = adapter

        layout_category.setOnClickListener {
            val intent = Intent(this, SelectChickenTypeActivity::class.java)
            startActivity(intent)
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.searchChickenInfo(p0)
            }
        })

        et_search.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_DEL){

                presenter.searchChickenInfo(et_search.text.toString())
            }
            false
        }

        presenter.apply {
            this.initChickenInfoVersion()
            this.checkChickenInfoVersion()
            this.searchChickenInfo("")
        }
    }

    override fun dialogShow() {
        LoadingDialog.instance.show(this)
    }

    override fun dialogDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun listClear() {
        adapter.clear()
    }

    override fun listRefresh() {
        adapter.refresh()
    }

    override fun addSearchResult(data: LocalChickenInfoData) {
        adapter.add(data)
    }
}
