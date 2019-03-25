package io.defy.chicken.lover.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.werb.pickphotoview.PickPhotoView
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.ProfileContract
import io.defy.chicken.lover.model.data.FileUploadData
import io.defy.chicken.lover.presenter.ProfilePresenter
import io.defy.chicken.lover.rxbus.ImagePickResultEvent
import io.defy.chicken.lover.rxbus.RxBus
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File

/**
 * Created by kim on 2017-09-20.
 */
class ProfileFragment : Fragment(), ProfileContract.View {

    private var presenter : ProfileContract.Presenter? = null

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_profile, container, false)

        presenter = ProfilePresenter()
        presenter?.attachView(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.getUserName()
        presenter?.getUserPoint()
        presenter?.getUserVisitTime()

        profile_image.setOnClickListener {
            when(presenter?.getLoginType())
            {
                0 -> {
                    val intent = Intent(activity, JoinActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    PickPhotoView.Builder(activity as ProfileActivity)
                        .setPickPhotoSize(1)                  // select image size
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
            }
        }

        profile_go_select_fb.setOnClickListener {
            val intent = Intent(activity, SelectFavoriteBrandActivity::class.java)
            startActivity(intent)
        }

        profile_go_select_ft.setOnClickListener {
            val intent = Intent(activity, SelectFavoriteTypeActivity::class.java)
            startActivity(intent)
        }

        /* Register Event */
        RxBus.listen(ImagePickResultEvent::class.java).subscribe{
            /* 이미지 경로값을 받아온 후 리스트에 배열*/
            if(it.from.equals("profile"))
            {
                val file = File(it.imagesPath.get(0))
                val imageUri = Uri.fromFile(file)

                Glide.with(activity as ProfileActivity)
                    .load(imageUri)
                    .into(profile_image)

                /* 확정 이후 서버에 업로드 */
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun setUserName(name: String) {
        profile_name.text = name
    }

    override fun setUserPoint(point: Int) {

    }

    override fun setUserVisitTime(visitTime: Int) {

    }
}