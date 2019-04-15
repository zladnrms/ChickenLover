package io.defy.chicken.lover.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.defy.chicken.lover.R
import io.defy.chicken.lover.adapter.view.FileUploadListAdapter
import io.defy.chicken.lover.contract.WriteContract
import io.defy.chicken.lover.model.data.FileUploadData
import io.defy.chicken.lover.presenter.WritePresenter
import java.io.File
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.werb.pickphotoview.PickPhotoView
import io.defy.chicken.lover.rxbus.ImagePickResultEvent
import io.defy.chicken.lover.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_write.*


/**
 * Created by kim on 2017-09-20.
 */
class WriteFragment : Fragment(), WriteContract.View {

    private var presenter: WriteContract.Presenter? = null
    private var adapter: FileUploadListAdapter? = null

    private var imagesPath : ArrayList<String>? = null

    companion object {
        fun newInstance(): WriteFragment {
            return WriteFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            it.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_write, container, false)

        presenter = WritePresenter()
        presenter?.attachView(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uploadFileList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        uploadFileList.hasFixedSize()
        adapter = FileUploadListAdapter((activity as BoardActivity), ArrayList<FileUploadData>())
        uploadFileList.adapter = adapter

        iv_submit.setOnClickListener {
            adapter?.let {
                val title = et_title.text.toString()
                val content = et_content.text.toString().replace("\n", "<br />")
                val imagesPath = it.getImagesPath()

                if(title.trim().equals("") || content.trim().equals(""))
                {
                    Toast.makeText(activity, "제목 혹은 내용을 작성해주세요", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    presenter?.write("free", title, content, imagesPath)
                }
            }
        }

        iv_upload.setOnClickListener {
            PickPhotoView.Builder(activity as BoardActivity)
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

        /* Register Event */
        RxBus.listen(ImagePickResultEvent::class.java).subscribe{
            /* 이미지 경로값을 받아온 후 리스트에 배열*/
            if(it.from.equals("board"))
            {
                adapter?.clear()
                it.imagesPath?.let {
                    for((index, item) in it.withIndex())
                    {
                        val bitmap = presenter?.imgPathToBitmap(item)
                        val data = FileUploadData(index, presenter?.getFileName(item, true), item, bitmap)
                        adapter?.add(data)
                        adapter?.refresh()
                    }
                }
            }
        }

    }

    override fun writeResultCallback(lastId: Int) {
        (activity as BoardActivity).supportFragmentManager.beginTransaction().remove(this).commit()
        (activity as BoardActivity).supportFragmentManager.popBackStack()
        switchFragment(ArticleFragment.newInstance(lastId), "article")
    }

    override fun switchFragment(fragment: Fragment, tag: String) {
        val fm = (activity as BoardActivity).supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun toastMsg(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        /* for memory */
        uploadFileList.adapter = null
        onDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }
}