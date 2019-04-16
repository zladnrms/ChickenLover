package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.werb.pickphotoview.util.PickConfig
import io.defy.chicken.lover.R
import io.defy.chicken.lover.rxbus.ImagePickResultEvent
import io.defy.chicken.lover.rxbus.RxBus
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        if(savedInstanceState == null) {
            val boardFragment : Fragment = BoardFragment()
            val fm : FragmentManager = supportFragmentManager
            val ft : FragmentTransaction = fm.beginTransaction()
            ft.replace(R.id.fragment_layout, boardFragment, "board")
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
                    var intent = Intent(this, ProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            Log.d("예아", "백스택체인지 : " + supportFragmentManager.backStackEntryCount)
        }
    }

    fun switchFragment(fragment: Fragment, tag: String) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
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
            RxBus.publish(ImagePickResultEvent("board",selectPaths))
        }
    }
}
