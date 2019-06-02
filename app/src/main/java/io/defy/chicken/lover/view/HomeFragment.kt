package io.defy.chicken.lover.view

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.google.firebase.analytics.FirebaseAnalytics
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.HomeContract
import kotlinx.android.synthetic.main.fragment_home.*
import io.defy.chicken.lover.presenter.HomePresenter
import io.defy.chicken.lover.util.RandomPickUtil
import io.defy.chicken.lover.view.custom.TypeView
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog


/**
 * Created by kim on 2017-09-20.
 */
class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var presenter: HomeContract.Presenter? = null

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = HomePresenter()
        presenter?.attachView(this)

        context?.let {
            firebaseAnalytics = FirebaseAnalytics.getInstance(it)
        }

        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_search.setOnClickListener {
            val intent = Intent(activity, SearchChickenInfoActivity::class.java)
            startActivity(intent)
        }

        card_chicken_info.setOnClickListener {
            val intent = Intent(activity, ChickenInfoActivity::class.java)
            presenter?.getChickenInfo()?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(activity, iv_chicken_img, "chickenImg")
                    intent.putExtra("infoId", it._id)
                    intent.putExtra("typeNumber", it.type_number)
                    startActivity(intent, options.toBundle())
                } else {
                    intent.putExtra("infoId", it._id)
                    intent.putExtra("typeNumber", it.type_number)
                    startActivity(intent)
                }
            }

            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "card_click")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }

        btn_id.setOnClickListener {
            val brand = RandomPickUtil.randomBrandPick()
            val type = RandomPickUtil.randomTypePick()

            presenter?.getChickenInfo("choice", brand, type)
        }

        ifNotNull(presenter?.pickBrand, presenter?.pickType) { b, t ->
            presenter?.getChickenInfo("choice", b, t)
        }
    }

    override fun showChickenInfo(way: String, name: String, brand: String, thumbs_up: Int) {
        layout_chicken_type.removeAllViewsInLayout()

        val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)

        tv_chicken_info_name.text = name
        tv_chicken_info_name.animation = fadeIn

        tv_chicken_info_brand.text = brand
        tv_chicken_info_brand.animation = fadeIn

        iv_chicken_img.setImageResource(R.drawable.fried)
        iv_chicken_img.animation = fadeIn
    }

    override fun showChickenType(item: String) {
        val tv = TypeView(context as MainActivity, item)
        layout_chicken_type.addView(tv)
    }

    override fun showChickenImage(drawable: Int) {
        iv_chicken_img.setImageResource(drawable)
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
        LoadingDialog.instance.show(activity as MainActivity)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(activity as MainActivity, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun onPause() {
        super.onPause()

        activity?.overridePendingTransition(0, 0)
    }
}