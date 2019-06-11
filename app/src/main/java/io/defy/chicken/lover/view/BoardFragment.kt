package io.defy.chicken.lover.view

import android.content.Context
import android.content.Intent
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

        //val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
        articleList.layoutManager = LinearLayoutManager(activity)
        articleList.hasFixedSize()
        articleList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = BoardArticleListAdapter((activity as MainActivity), ArrayList())
        articleList.adapter = adapter

        iv_write.setOnClickListener {
            val intent = Intent(activity, WriteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
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

    private fun resetArticleList() {
        presenter?.setIndex(0)
        adapter?.clear()
        adapter?.refresh()
        presenter?.getArticleList()
    }

    override fun switchFragment(fragment: Fragment, tag: String) {
        val fm = (activity as MainActivity).supportFragmentManager
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
        LoadingDialog.instance.show(activity as MainActivity)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(this as MainActivity, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}