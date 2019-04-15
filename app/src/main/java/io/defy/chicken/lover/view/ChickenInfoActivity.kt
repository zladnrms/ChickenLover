package io.defy.chicken.lover.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.presenter.ChickenInfoPresenter
import kotlinx.android.synthetic.main.activity_chicken_info.*

class ChickenInfoActivity : AppCompatActivity(), ChickenInfoContract.View {

    private var presenter: ChickenInfoContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chicken_info)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { v -> finish() }

        presenter = ChickenInfoPresenter()
        presenter?.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}
