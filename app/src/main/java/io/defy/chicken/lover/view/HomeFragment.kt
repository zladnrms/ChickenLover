package io.defy.chicken.lover.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.HomeContract
import kotlinx.android.synthetic.main.fragment_home.*
import io.defy.chicken.lover.presenter.HomePresenter
import io.defy.chicken.lover.util.RandomPickUtil
import org.json.JSONObject


/**
 * Created by kim on 2017-09-20.
 */
class HomeFragment : Fragment(), HomeContract.View {

    private var presenter : HomeContract.Presenter? = null
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        presenter = HomePresenter()
        presenter?.attachView(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_search.setOnClickListener {
            var intent = Intent(activity, SearchChickenInfoActivity::class.java)
            startActivity(intent)
        }

        btn_id.setOnClickListener {
            val data = RandomPickUtil.randomBrandPick()
            val data2 = RandomPickUtil.randomTypePick()

            presenter?.getChickenInfo("choice", data, data2)
            Log.d("ddd", "ㅇㅇ"+data +"ㅇㅇ" + data2)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun showChickenInfo(way: String, name: String, brand: String) {

        val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)

        tv_chicken_info_name.text = name
        tv_chicken_info_name.animation = fadeIn

        tv_chicken_info_brand.text = brand
        tv_chicken_info_brand.animation = fadeIn

        iv_picked_chicken.setImageResource(R.drawable.fried)
        iv_picked_chicken.animation = fadeIn
    }

    override fun showChickenImage(drawable: Int) {
        iv_picked_chicken.setImageResource(drawable)
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

    override fun loadingShow() {
        LoadingDialog.instance.show(activity as HomeActivity)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(activity as HomeActivity, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }
}