package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.deco.GridSpacingItemDecoration
import io.defy.chicken.lover.adapter.view.SelectChickenTypeAdapter
import io.defy.chicken.lover.contract.SelectChickenTypeContract
import io.defy.chicken.lover.contract.SplashContract
import io.defy.chicken.lover.model.data.SelectChickenTypeData
import io.defy.chicken.lover.presenter.SelectChickenTypePresenter
import io.defy.chicken.lover.presenter.SplashPresenter
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_select_chicken_type.*
import java.util.ArrayList

class SelectChickenTypeActivity : BaseActivity(), SelectChickenTypeContract.View {

    private lateinit var adapter : SelectChickenTypeAdapter
    private val presenter: SelectChickenTypeContract.Presenter by lazy {
        SelectChickenTypePresenter().apply { attachView(this) }
    }

    private val spanCount = 3
    private val spacing = 30
    private val includeEdge = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_chicken_type)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        val mGridLayoutManager = GridLayoutManager(this, 3)
        typeList.apply {
            this.layoutManager = mGridLayoutManager
            this.hasFixedSize()
            this.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
        typeList.adapter = adapter

        adapter = SelectChickenTypeAdapter(this, presenter.getChickenTypeList())
    }
}
