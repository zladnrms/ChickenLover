package io.defy.chicken.lover.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.deco.GridSpacingItemDecoration
import io.defy.chicken.lover.adapter.view.FavoriteTypeAdapter
import io.defy.chicken.lover.contract.SelectFavoriteTypeContract
import io.defy.chicken.lover.model.data.FavoriteTypeData
import io.defy.chicken.lover.presenter.SelectFavoriteTypePresenter
import kotlinx.android.synthetic.main.activity_select_favorite_type.*

class SelectFavoriteTypeActivity : BaseActivity(), SelectFavoriteTypeContract.View {

    private lateinit var adapter : FavoriteTypeAdapter
    private val presenter: SelectFavoriteTypeContract.Presenter by lazy {
        SelectFavoriteTypePresenter().apply {
            attachView(this@SelectFavoriteTypeActivity)
            initFirstType()
        }
    }

    private val spanCount = 3
    private val spacing = 30
    private val includeEdge = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_favorite_type)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        val mGridLayoutManager = GridLayoutManager(this, 3)
        recyclerview.apply {
            this.layoutManager = mGridLayoutManager
            this.hasFixedSize()
            this.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        }
        recyclerview.adapter = adapter
        adapter = FavoriteTypeAdapter(this, ArrayList())

        /* init first page (because presenter's page initial value is Zero ( 0 ) )*/
        nextPageClick()

        favorite_type_next.setOnClickListener {
            nextPageClick()
        }

        favorite_type_previous.setOnClickListener {
            previousPageClick()
        }

        favorite_type_submit.setOnClickListener {
            presenter.submit()
        }
    }

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R) {
        if (a != null && b != null) {
            code(a, b)
        }
    }

    override fun nextPageClick() {
        ifNotNull(adapter, presenter) { adapter, presenter ->
            presenter.nextPage()
            adapter.clear()
            adapter.addFB(presenter.getPage())
            adapter.refresh()
            if (presenter.getPage() == 2) {
                favorite_type_next.visibility = View.GONE
            }
            if (presenter.getPage() != 1) {
                favorite_type_previous.visibility = View.VISIBLE
            }
        }
    }

    override fun previousPageClick() {
        ifNotNull(adapter, presenter) { adapter, presenter ->
            presenter.previousPage()
            adapter.clear()
            adapter.addFB(presenter.getPage())
            adapter.refresh()
            if (presenter.getPage() == 1) {
                favorite_type_previous.visibility = View.GONE
            }
            favorite_type_next.visibility = View.VISIBLE
        }
    }
}
