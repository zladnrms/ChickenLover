package io.defy.chicken.lover.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.HomeContract
import io.defy.chicken.lover.contract.RankNInfoContract
import io.defy.chicken.lover.presenter.HomePresenter
import io.defy.chicken.lover.presenter.RankNInfoPresenter


/**
 * Created by kim on 2017-09-20.
 */
class RankNInfoFragment : Fragment(), RankNInfoContract.View {

    private var presenter : RankNInfoContract.Presenter? = null
    companion object {
        fun newInstance(): RankNInfoFragment {
            return RankNInfoFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rank_n_info, container, false)

        presenter = RankNInfoPresenter().apply { attachView(this@RankNInfoFragment) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }

    inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R) {
        if (a != null && b != null) {
            code(a, b)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}