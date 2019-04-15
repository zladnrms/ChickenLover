package io.defy.chicken.lover.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.LoginContract
import io.defy.chicken.lover.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter()
        presenter?.attachView(this)

        btn_login.setOnClickListener {
            val id = et_id.text.toString().trim()
            val password = et_password.text.toString().trim()
            presenter?.login("mobile", 1, id, password)
        }
    }

    override fun complete() {
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