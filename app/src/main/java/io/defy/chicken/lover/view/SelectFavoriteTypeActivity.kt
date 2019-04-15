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

class SelectFavoriteTypeActivity : AppCompatActivity(), SelectFavoriteTypeContract.View {

    private var presenter: SelectFavoriteTypeContract.Presenter? = null
    private var adapter: FavoriteTypeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_favorite_type)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        presenter = SelectFavoriteTypePresenter()
        presenter?.attachView(this)
        presenter?.initFirstType()

        /*
         * 선호하는 치킨 브랜드 고르기 recyclerview
         */
        val mGridLayoutManager = GridLayoutManager(this, 3)
        recyclerview_favorite_type.layoutManager = mGridLayoutManager
        recyclerview_favorite_type.hasFixedSize()
        val spanCount = 3 // 3 columns
        val spacing = 30 // 간격 (px)
        val includeEdge = true
        recyclerview_favorite_type.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        adapter = FavoriteTypeAdapter(
            this,
            ArrayList<FavoriteTypeData>()
        )
        recyclerview_favorite_type.adapter = adapter

        /* init first page (because presenter's page initial value is Zero ( 0 ) )*/
        nextPageClick()

        favorite_type_next.setOnClickListener {
            nextPageClick()
        }

        favorite_type_previous.setOnClickListener {
            previousPageClick()
        }

        favorite_type_submit.setOnClickListener {
            presenter?.submit()
        }
    }

    /*
     * 2개 이상의 null check용
     * 사용법 :
     * ifNotNull(변수1, 변수2) { 변수1, 변수2 -> 처리 }
     */
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
    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}
