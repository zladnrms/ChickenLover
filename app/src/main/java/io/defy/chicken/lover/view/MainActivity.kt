package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.werb.pickphotoview.util.PickConfig
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.MainContract
import io.defy.chicken.lover.presenter.MainPresenter
import io.defy.chicken.lover.rxbus.ImagePickResultEvent
import io.defy.chicken.lover.rxbus.RxBus
import kotlinx.android.synthetic.main.activity_main.*

class
MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter : MainContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()
        presenter?.attachView(this)

        if(savedInstanceState == null) {
            val homeFragment : Fragment = HomeFragment()
            val fm : FragmentManager = supportFragmentManager
            val ft : FragmentTransaction = fm.beginTransaction()
            ft.replace(R.id.fragment_layout, homeFragment, "home")
            ft.commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId)
            {
                R.id.action_home -> {
                    //switchFragment(HomeFragment(), "home")
                    var intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    true
                }
                R.id.action_board -> {
                    var intent = Intent(this, BoardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    //switchFragment(BoardFragment(), "board")
                true
                }
                R.id.action_sale_info -> {
                    var intent = Intent(this, RankNInfoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    //switchFragment(RankNInfoFragment(), "rank_n_info")
                    true
                }
                R.id.action_profile -> {
                    var intent = Intent(this, ProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                    //switchFragment(ProfileFragment(), "profile")
                    true
                }
                else -> false
            }
        }
    }

    override fun switchFragment(fragment: Fragment, tag: String) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val fm = supportFragmentManager
        fm.popBackStackImmediate()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
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
