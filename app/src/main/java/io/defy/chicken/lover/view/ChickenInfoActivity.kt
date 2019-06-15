package io.defy.chicken.lover.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.model.data.ChickenInfoData
import io.defy.chicken.lover.presenter.ChickenInfoPresenter
import io.defy.chicken.lover.view.dialog.AlertDialog
import kotlinx.android.synthetic.main.activity_chicken_info.*



class ChickenInfoActivity : BaseActivity(), ChickenInfoContract.View {

    private val presenter: ChickenInfoContract.Presenter by lazy {
        ChickenInfoPresenter().apply { attachView(this@ChickenInfoActivity) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chicken_info)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        setDataFromIntent()

        presenter.getChickenInfo(presenter.getInfoId())
    }


    private fun setDataFromIntent() {
        val intent = intent
        presenter.apply {
            setTypeNumber(intent.getIntExtra("typeNumber", 0))
            setInfoId(intent.getIntExtra("infoId", 0))
            setChickenImage()
        }
    }

    override fun setImageResource(drawable: Int) {
        iv_chicken_img.setImageResource(drawable)
    }

    override fun setChickenInfo(chickenInfoData: ChickenInfoData?) {
        chickenInfoData?.let {
            tv_chicken_brand.text = it.brand
            tv_chicken_name.text = it.name
        }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

    }

    override fun alertShow() {
        AlertDialog.instance.show(this, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
