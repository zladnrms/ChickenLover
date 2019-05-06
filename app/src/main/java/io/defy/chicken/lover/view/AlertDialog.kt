package io.defy.chicken.lover.view

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import io.defy.chicken.lover.R
import kotlinx.android.synthetic.main.dialog_alert.*


class AlertDialog {

    companion object {
        val instance = AlertDialog()
    }

    private val LAYOUT = R.layout.dialog_alert

    private var dialog: Dialog? = null

    fun show(context: Context, symptom: String, detail: String) {
        if(dialog == null)
        {
            dialog = Dialog(context)
            dialog?.apply {
                this.requestWindowFeature(Window.FEATURE_NO_TITLE)
                this.setContentView(LAYOUT)
                this.setCancelable(true)
                this.show()

                val tv_symptom = this.findViewById(R.id.tv_symptom) as TextView
                tv_symptom.text = symptom
                val tv_detail = this.findViewById(R.id.tv_detail) as TextView
                tv_detail.text = detail

                tv_close.setOnClickListener {
                    this.dismiss()
                }
            }
        }
    }

    fun dismiss() {
        dialog!!.dismiss()
    }
}
