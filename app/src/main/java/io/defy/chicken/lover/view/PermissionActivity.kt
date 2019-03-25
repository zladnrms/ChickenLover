package io.defy.chicken.lover.view

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_permission.*
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.design.widget.Snackbar
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.PermissionContract
import io.defy.chicken.lover.presenter.PermissionPresenter


class PermissionActivity : AppCompatActivity(), PermissionContract.View {

    /* presenter */
    private var presenter: PermissionContract.Presenter? = null

    private var showCamera = true
    private var showStorage = true

    val PERMISSIONS_MULTIPLE_REQUEST = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        presenter = PermissionPresenter()
        presenter?.attachView(this)

        layoutCameraCollapse.setOnClickListener{
            if(showCamera) {
                showCamera = false;
                ivCamera.setImageResource(R.drawable.ic_chevron_down);
                layoutCameraDetail.setVisibility(View.GONE);
            } else {
                showCamera = true;
                ivCamera.setImageResource(R.drawable.ic_chevron_up);
                layoutCameraDetail.setVisibility(View.VISIBLE);
            }
        }

        layoutStorageCollapse.setOnClickListener{
            if(showStorage) {
                showStorage = false;
                ivStorage.setImageResource(R.drawable.ic_chevron_down);
                layoutStorageDetail.setVisibility(View.GONE);
            } else {
                showStorage = true;
                ivStorage.setImageResource(R.drawable.ic_chevron_up);
                layoutStorageDetail.setVisibility(View.VISIBLE);
            }
        }

        btnAccept.setOnClickListener{
            checkAndroidVersion()
        }
    }

    override fun onResume() {
        super.onResume()

        checkAndroidVersion()
    }

    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            val intent = Intent(this@PermissionActivity,  HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /* Permission Check */
    private fun checkPermission() {
        if ((ContextCompat.checkSelfPermission(this@PermissionActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(this@PermissionActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this@PermissionActivity, Manifest.permission.CAMERA)) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this@PermissionActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this@PermissionActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this@PermissionActivity, Manifest.permission.CAMERA)) {

                Snackbar.make(this@PermissionActivity.findViewById(android.R.id.content),
                        "어플 사용의 원활한 진행을 위해 권한을 허용해주세요",
                        Snackbar.LENGTH_INDEFINITE).setAction("설정") { v ->
                    ActivityCompat.requestPermissions(this@PermissionActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                            PERMISSIONS_MULTIPLE_REQUEST)

                }.show()
            } else {
                ActivityCompat.requestPermissions(this@PermissionActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                        PERMISSIONS_MULTIPLE_REQUEST)
            }
        } else {
            // if permission already granted
            val intent = Intent(this@PermissionActivity,  HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_MULTIPLE_REQUEST -> if (grantResults.size > 0) {
                val readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeExternalFile = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val camera = grantResults[2] == PackageManager.PERMISSION_GRANTED

                if (readExternalFile && writeExternalFile && camera) {
                    // if permission granted
                    val intent = Intent(this@PermissionActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Snackbar.make(this@PermissionActivity.findViewById(android.R.id.content),
                            "어플 사용의 원활한 진행을 위해 권한을 허용해주세요",
                            Snackbar.LENGTH_INDEFINITE).setAction("설정"
                    ) { v ->
                        ActivityCompat.requestPermissions(this@PermissionActivity,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                                PERMISSIONS_MULTIPLE_REQUEST)
                    }.show()
                }
            }
        }
    }

}
