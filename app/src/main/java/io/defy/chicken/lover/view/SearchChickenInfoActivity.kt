package io.defy.chicken.lover.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.ChatListAdapter
import io.defy.chicken.lover.contract.SearchChickenInfoContract
import io.defy.chicken.lover.model.data.ChatData
import io.defy.chicken.lover.presenter.SearchChickenInfoPresenter
import kotlinx.android.synthetic.main.activity_search_chicken_info.*

class SearchChickenInfoActivity : AppCompatActivity(), SearchChickenInfoContract.View {

    private var presenter: SearchChickenInfoContract.Presenter? = null
    private var adapter : ChatListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_chicken_info)

        presenter = SearchChickenInfoPresenter()
        presenter?.attachView(this)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        searchList.layoutManager = LinearLayoutManager(this)
        searchList.hasFixedSize()
        adapter = ChatListAdapter(this, ArrayList<ChatData>())
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

        presenter?.initChickenInfoVersion()
        presenter?.checkChickenInfoVersion()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}
