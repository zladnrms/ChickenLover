package io.defy.chicken.lover.view

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.ImageView
import io.defy.chicken.lover.R


class CustomDialog {

    companion object {
        val instance = CustomDialog()
    }

    private val LAYOUT = R.layout.dialog_loading

    private var dialog: Dialog? = null

    private var runnable: ChangeImage? = null
    private var thread: Thread? = null

    fun show(context: Context) {
        dialog = Dialog(context)
        dialog?.apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setContentView(LAYOUT)
            this.setCancelable(true)
            this.show()

            val iv_loading = this.findViewById(R.id.iv_loading) as ImageView

            runnable = ChangeImage(iv_loading)

            thread = Thread(runnable)
            thread?.apply {
                this.isDaemon = true
                this.start()
            }
        }
        dialog?.setOnDismissListener {
            runnable?.stop()
        }
        dialog?.setOnCancelListener {
            runnable?.stop()
        }
    }

    fun dismiss() {
        dialog?.apply { this.dismiss() }
    }

    inner class ChangeImage constructor(iv_loading: ImageView) : Runnable {

        var flag = true
        var type = 0
        var time = 0
        var iv_loading: ImageView? = null

        init {
            this.iv_loading = iv_loading
        }

        override fun run() {
            while (flag) {

                this.iv_loading?.let {
                    when (type) {
                        0 -> it.setImageResource(R.drawable.fried)
                        1 -> it.setImageResource(R.drawable.seasoned_fried)
                        2 -> it.setImageResource(R.drawable.cheese_fried)
                        else -> {
                            type = 0
                            it.setImageResource(R.drawable.fried)
                        }
                    }

                    Log.d("커스텀다이얼로그★", "커스텀다이얼로그작동중 : " + type)

                    type++
                    time++

                    Thread.sleep(400)
                }

                if(time == 10) // setTimeOut 4초, (10번하면 dialog 없앰)
                {
                    flag = false
                    dismiss()
                }
            }
        }

        fun stop()
        {
            flag = false
        }
    }
}
