package io.defy.chicken.lover.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.BoardArticleListAdapter
import io.defy.chicken.lover.contract.BoardContract
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.network.response.BoardArticleRes
import io.defy.chicken.lover.presenter.BoardPresenter
import kotlinx.android.synthetic.main.fragment_board.*

/**
 * Created by kim on 2017-09-20.
 */
class BoardFragment : Fragment(), BoardContract.View {

    private var presenter : BoardContract.Presenter? = null
    private var adapter : BoardArticleListAdapter? = null

    private var index = 0
    private var limit = 15

    companion object {
        fun newInstance(): BoardFragment {
            return BoardFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_board, container, false)

        presenter = BoardPresenter()
        presenter?.attachView(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleList.layoutManager = LinearLayoutManager(activity)
        articleList.hasFixedSize()
        adapter = BoardArticleListAdapter((activity as BoardActivity), ArrayList<BoardArticleData>())
        articleList.adapter = adapter

        iv_write.setOnClickListener {
            (activity as BoardActivity).switchFragment(WriteFragment(), "write")
        }
    }

    override fun onResume() {
        super.onResume()

        adapter?.clear()
        presenter?.getArticleList("free", index, limit)
    }

    override fun switchFragment(fragment: Fragment, tag: String) {
        val fm = (activity as BoardActivity).supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun setArticleList(item : BoardArticleData) {
        adapter?.add(item)
        adapter?.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        /* for memory */
        articleList.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}