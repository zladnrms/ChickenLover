package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.SplashContract
import io.defy.chicken.lover.presenter.SplashPresenter
import kotlinx.android.synthetic.main.activity_chicken_info.*

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private var presenter: SplashContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter()
        presenter?.attachView(this)

        presenter?.login()
    }

    override fun pass() {
        val intent = Intent(this, PermissionActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    }
    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}
