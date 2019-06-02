package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.SplashContract
import io.defy.chicken.lover.presenter.SplashPresenter
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog

class SplashActivity : BaseActivity(), SplashContract.View {

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


    override fun onBackPressed() {

    }

    override fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    }

    override fun onResume() {
        super.onResume()

        LoadingDialog.instance.show(this)
    }

    override fun onStop() {
        super.onStop()

        LoadingDialog.instance.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }

    override fun alertShow() {
        AlertDialog.instance.show(this, "연결 끊김", "네트워크 연결 상태를 확인해주세요")
    }

    override fun alertDismiss() {
        AlertDialog.instance.dismiss()
    }

    override fun onPause() {
        super.onPause()
    }
}
