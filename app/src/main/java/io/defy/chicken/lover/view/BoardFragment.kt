package io.defy.chicken.lover.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.BoardArticleListAdapter
import io.defy.chicken.lover.contract.BoardContract
import io.defy.chicken.lover.model.data.BoardArticleData
import io.defy.chicken.lover.presenter.BoardPresenter
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_board.*

/**
 * Created by kim on 2017-09-20.
 */
class BoardFragment : Fragment(), BoardContract.View {

    private var fView : View? = null
    private var presenter : BoardContract.Presenter? = null
    private var adapter : BoardArticleListAdapter? = null

    companion object {
        fun newInstance(): BoardFragment {
            return BoardFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(fView == null)
            fView = inflater.inflate(R.layout.fragment_board, container, false)
        //val view = inflater.inflate(R.layout.fragment_board, container, false)

        if(presenter == null)
        {
            presenter = BoardPresenter()
            presenter?.attachView(this)
        }

        return fView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
        articleList.layoutManager = LinearLayoutManager(activity)
        articleList.hasFixedSize()
        articleList.addItemDecoration(dividerItemDecoration)
        adapter = BoardArticleListAdapter((activity as BoardActivity), ArrayList())
        articleList.adapter = adapter

        iv_write.setOnClickListener {
            (activity as BoardActivity).switchFragment(WriteFragment(), "write")
        }

        layout_category.setOnClickListener {
            layout_category_selector.visibility = View.VISIBLE
        }

        tv_category_notice.setOnClickListener {
            val type = getString(R.string.board_category_info_type)
            tv_category.text = getString(R.string.board_category_info)
            changeCategory(type)
        }

        tv_category_free.setOnClickListener {
            val type = getString(R.string.board_category_free_type)
            tv_category.text = getString(R.string.board_category_free)
            changeCategory(type)
        }

        iv_category_close.setOnClickListener {
            layout_category_selector.visibility = View.GONE
        }

        presenter?.apply {
            this.setRecyclerViewScrollListener(articleList)
            changeCategory(this.getType())
        }
    }

    private fun changeCategory(type: String) {
        presenter?.setType(type)
        adapter?.setType(type)
        resetArticleList()
        layout_category_selector.visibility = View.GONE
    }


    // 왓을때 index = 0 limit 15
    // 다시오면 그대로여야만함 -> onREsume 에서 별도 작업x
    // 아래로가면 15개씩 추가 됨 -> clear 없이 15개 add
    // 글 쓰면 clear 후 index는 0부터 다시 받아옴 -> clkear, index 0
    // 카테고리 바꾸면 clear 후 index는 0부터 다시 받아옴

    private fun resetArticleList() {
        presenter?.setIndex(0)
        adapter?.clear()
        adapter?.refresh()
        presenter?.getArticleList()
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

    override fun loadingShow() {
        LoadingDialog.instance.show(activity as BoardActivity)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(this as BoardActivity, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}