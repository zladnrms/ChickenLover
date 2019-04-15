package io.defy.chicken.lover.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            var intent = Intent(activity, BoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
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

    override fun showChickenInfo(way: String, name: String, brand: String, type: JSONObject) {
        tv_chicken_info_name.text = name
        tv_chicken_info_brand.text = brand

        var type_array = ArrayList<String>()
        for(key : String in type.keys())
        {
            type_array.add(type.get(key).toString())
        }
        for(item in type_array)
        {
            Log.d("로그 : ", item)
        }
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
}