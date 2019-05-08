package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.LoginContract
import io.defy.chicken.lover.presenter.LoginPresenter
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        presenter = LoginPresenter()
        presenter?.attachView(this)

        btn_login.setOnClickListener {
            val id = et_id.text.toString().trim()
            val password = et_password.text.toString().trim()
            presenter?.login("mobile", 1, id, password)
        }

        btn_go_join.setOnClickListener {
            val intent = Intent(this,  JoinActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun loadingShow() {
        LoadingDialog.instance.show(this)
    }

    override fun loadingDismiss() {
        LoadingDialog.instance.dismiss()
    }

    override fun alertShow() {
        AlertDialog.instance.show(this, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
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
