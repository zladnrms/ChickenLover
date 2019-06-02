package io.defy.chicken.lover.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import io.defy.chicken.lover.contract.MainContract
import io.defy.chicken.lover.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import com.werb.pickphotoview.extensions.string
import io.defy.chicken.lover.R


class MainActivity : AppCompatActivity(), MainContract.View {

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

        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottom_navigation.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener)
        bottom_navigation.selectedItemId = R.id.action_home
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                switchFragment(HomeFragment.newInstance(), "home", string(R.string.toolbar_title_home))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_board -> {
                switchFragment(BoardFragment.newInstance(), "board", string(R.string.toolbar_title_board))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_chat -> {
                switchFragment(ChatFragment.newInstance(), "chat", string(R.string.toolbar_title_chat))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_sale_info -> {
                switchFragment(RankNInfoFragment.newInstance(), "info", string(R.string.toolbar_title_info))
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                switchFragment(ProfileFragment.newInstance(), "profile", string(R.string.toolbar_title_profile))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val onNavigationItemReselectedListener = object : BottomNavigationView.OnNavigationItemReselectedListener {

        override fun onNavigationItemReselected(item: MenuItem) {
            Toast.makeText(this@MainActivity, "Reselected", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
            R.id.action_board -> {
                val intent = Intent(this, BoardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
            R.id.action_chat -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_sale_info -> {
                val intent = Intent(this, RankNInfoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
            R.id.action_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
        }
        false
    }
    */
    override fun switchFragment(fragment: Fragment, tag: String, title: String) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
        toolbar_title.text = title
    }

    override fun toastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(bottom_navigation.selectedItemId == R.id.action_home){
            super.onBackPressed()
        }else{
            bottom_navigation.selectedItemId = R.id.action_home
        }
    }

}
