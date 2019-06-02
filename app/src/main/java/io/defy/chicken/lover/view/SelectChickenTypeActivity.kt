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

    private var presenter: SelectChickenTypeContract.Presenter? = null
    private var adapter : SelectChickenTypeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_chicken_type)

        presenter = SelectChickenTypePresenter()
        presenter?.attachView(this)

        val mGridLayoutManager = GridLayoutManager(this, 3)
        typeList.layoutManager = mGridLayoutManager
        typeList.hasFixedSize()
        val spanCount = 3 // 3 columns
        val spacing = 30 // 간격 (px)
        val includeEdge = true
        typeList.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        adapter = SelectChickenTypeAdapter(
            this,
            arrayListOf(
                SelectChickenTypeData(0,"후라이드"),
                SelectChickenTypeData(1,"양념"),
                SelectChickenTypeData(2,"치즈"),
                SelectChickenTypeData(3,"간장"),
                SelectChickenTypeData(4,"파닭"),
                SelectChickenTypeData(5,"갈릭"),
                SelectChickenTypeData(6,"매운맛")
            )
        )
        typeList.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
