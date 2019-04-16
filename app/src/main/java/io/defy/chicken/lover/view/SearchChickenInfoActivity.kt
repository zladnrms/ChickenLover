package io.defy.chicken.lover.view

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.SearchChickenInfoContract
import io.defy.chicken.lover.presenter.SearchChickenInfoPresenter
import kotlinx.android.synthetic.main.activity_home.*

class SearchChickenInfoActivity : AppCompatActivity(), SearchChickenInfoContract.View {

    private var presenter: SearchChickenInfoContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_chicken_info)

        presenter = SearchChickenInfoPresenter()
        presenter?.attachView(this)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        presenter?.checkChickenInfoVersion()
    }
}
