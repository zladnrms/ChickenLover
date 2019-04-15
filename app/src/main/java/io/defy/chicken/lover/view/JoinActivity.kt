package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.JoinContract
import io.defy.chicken.lover.presenter.JoinPresenter
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity(), JoinContract.View {

    private var presenter: JoinContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        presenter = JoinPresenter()
        presenter?.attachView(this)

        btn_join.setOnClickListener {
            val id = et_id.text.toString().trim()
            val password = et_password.text.toString().trim()
            val name = et_name.text.toString().trim()
            presenter?.join(id, password, name)
        }

        btn_go_login.setOnClickListener {
            val intent = Intent(this,  LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    }

    override fun complete() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}
