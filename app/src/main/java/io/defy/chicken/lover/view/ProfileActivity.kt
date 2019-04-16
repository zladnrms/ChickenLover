package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.werb.pickphotoview.util.PickConfig
import io.defy.chicken.lover.R
import io.defy.chicken.lover.rxbus.ImagePickResultEvent
import io.defy.chicken.lover.rxbus.RxBus
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if(savedInstanceState == null) {
            val profileFragment : Fragment = ProfileFragment()
            val fm : FragmentManager = supportFragmentManager
            val ft : FragmentTransaction = fm.beginTransaction()
            ft.replace(R.id.fragment_layout, profileFragment, "profile")
            ft.commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId)
            {
                R.id.action_home -> {
                    var intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
                }
                R.id.action_board -> {
                    var intent = Intent(this, BoardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
                }
                R.id.action_chat -> {
                    var intent = Intent(this, ChatActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
                }
                R.id.action_sale_info -> {
                    var intent = Intent(this, RankNInfoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
                }
                R.id.action_profile -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val fm = supportFragmentManager
        fm.popBackStackImmediate()
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
            // do something u want
            RxBus.publish(ImagePickResultEvent("profile",selectPaths))
        }
    }
}
