package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.JoinContract
import io.defy.chicken.lover.presenter.JoinPresenter
import kotlinx.android.synthetic.main.activity_join.*
import android.text.InputFilter
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import java.util.regex.Pattern


class JoinActivity : BaseActivity(), JoinContract.View {

    private var presenter: JoinContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        presenter = JoinPresenter().apply { attachView(this@JoinActivity) }

        et_id.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")
            if (source == "" || ps.matcher(source).matches()) {
                return@InputFilter source
            }
            Toast.makeText(this, "한글, 영문, 숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
            ""
        }, InputFilter.LengthFilter(9))

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

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}
