package io.defy.chicken.lover.view

import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()

        val fm = supportFragmentManager
        fm.popBackStackImmediate()
    }

    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, 0)
    }

    override fun onResume() {
        this.overridePendingTransition(0,0);
        super.onResume()
    }
}
