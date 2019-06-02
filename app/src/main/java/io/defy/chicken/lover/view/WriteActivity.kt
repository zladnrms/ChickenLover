package io.defy.chicken.lover.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.werb.pickphotoview.PickPhotoView
import com.werb.pickphotoview.util.PickConfig
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.FileUploadListAdapter
import io.defy.chicken.lover.contract.WriteContract
import io.defy.chicken.lover.model.data.FileUploadData
import io.defy.chicken.lover.presenter.WritePresenter
import io.defy.chicken.lover.rxbus.ImagePickResultEvent
import io.defy.chicken.lover.rxbus.RxBus
import io.defy.chicken.lover.view.dialog.AlertDialog
import io.defy.chicken.lover.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : BaseActivity(), WriteContract.View {

    private var presenter: WriteContract.Presenter? = null
    private var adapter: FileUploadListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        this.apply {
            this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        presenter = WritePresenter()
        presenter?.attachView(this)

        uploadFileList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        uploadFileList.hasFixedSize()
        adapter = FileUploadListAdapter(this, ArrayList<FileUploadData>())
        uploadFileList.adapter = adapter

        iv_submit.setOnClickListener {
            adapter?.let {
                val title = et_title.text.toString()
                val content = et_content.text.toString().replace("\n", "<br />")
                val imagesPath = it.getImagesPath()

                if (title.trim().equals("") || content.trim().equals("")) {
                    Toast.makeText(this, "제목 혹은 내용을 작성해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    presenter?.write(title, content, imagesPath)
                }
            }
        }

        iv_upload.setOnClickListener {
            PickPhotoView.Builder(this)
                .setPickPhotoSize(4)                  // select image size
                .setClickSelectable(true)             // click one image immediately close and return image
                .setShowCamera(true)                  // is show camera
                .setSpanCount(4)                      // span count
                .setLightStatusBar(true)              // lightStatusBar used in Android M or higher
                .setStatusBarColor(R.color.pick_white)     // statusBar color
                .setToolbarColor(R.color.pick_white)       // toolbar color
                .setToolbarTextColor(R.color.pick_black)   // toolbar text color
                .setSelectIconColor(R.color.pick_black)     // select icon color
                .setShowGif(true)                    // is show gif
                .start()
        }

        layout_category.setOnClickListener {
            layout_category_selector.visibility = View.VISIBLE
        }

        tv_category_notice.setOnClickListener {
            tv_category.text = "실시간정보"
            presenter?.setType("info")
            layout_category_selector.visibility = View.GONE
        }

        tv_category_free.setOnClickListener {
            tv_category.text = "자유게시판"
            presenter?.setType("free")
            layout_category_selector.visibility = View.GONE
        }

        iv_category_close.setOnClickListener {
            layout_category_selector.visibility = View.GONE
        }

        /* Register Event */
        RxBus.listen(ImagePickResultEvent::class.java).subscribe {
            /* 이미지 경로값을 받아온 후 리스트에 배열*/
            if (it.from.equals("board")) {
                adapter?.clear()
                it.imagesPath?.let {
                    for ((index, item) in it.withIndex()) {
                        val bitmap = presenter?.imgPathToBitmap(item)
                        val data = FileUploadData(index, presenter?.getFileName(item, true), item, bitmap)
                        adapter?.add(data)
                        adapter?.refresh()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 0) {
            return
        }
        if (data == null) {
            return
        }
        /* WriteFragment로부터 이미지 선택 시 path를 WriteFragment로 전송 */
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            val selectPaths: ArrayList<String> =
                data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT) as ArrayList<String>

            selectPaths?.let {
                for ((index, item) in it.withIndex()) {
                    val bitmap = presenter?.imgPathToBitmap(item)
                    val data = FileUploadData(index, presenter?.getFileName(item, true), item, bitmap)
                    adapter?.add(data)
                    adapter?.refresh()
                }
            }
        }
    }

    override fun writeResultCallback() {
        toastMsg("글을 작성하셨습니다")
        finish()
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

    override fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        uploadFileList.adapter = null
        presenter?.detachView(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
