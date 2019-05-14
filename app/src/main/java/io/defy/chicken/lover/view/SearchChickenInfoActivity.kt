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


class SearchChickenInfoActivity : AppCompatActivity(), SearchChickenInfoContract.View {

    private var presenter: SearchChickenInfoContract.Presenter? = null
    private var adapter : SearchChickenInfoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_chicken_info)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        presenter = SearchChickenInfoPresenter()
        presenter?.attachView(this)

        val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)
        searchList.layoutManager = LinearLayoutManager(this)
        searchList.hasFixedSize()
        searchList.addItemDecoration(dividerItemDecoration)
        adapter = SearchChickenInfoListAdapter(this, ArrayList())
        searchList.adapter = adapter

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter?.searchChickenInfo(p0)
            }
        })

        et_search.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_DEL){

                presenter?.searchChickenInfo(et_search.text.toString())
            }
            false
        }

        presenter?.apply {
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

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }

    override fun listClear() {
        adapter?.clear()
    }

    override fun listRefresh() {
        adapter?.refresh()
    }

    override fun addSearchResult(data: LocalChickenInfoData) {
        adapter?.add(data)
    }

    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, 0)
    }
}
