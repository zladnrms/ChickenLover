package io.defy.chicken.lover.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import io.defy.chicken.lover.R
import io.defy.chicken.lover.util.BottomNavigationHelper
import kotlinx.android.synthetic.main.activity_rank_n_info.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        if(savedInstanceState == null) {
            val rankNInfoFragment : Fragment = ChatFragment()
            val fm : FragmentManager = supportFragmentManager
            val ft : FragmentTransaction = fm.beginTransaction()
            ft.replace(R.id.fragment_layout, rankNInfoFragment, "chat")
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
                else -> true
            }
        }
        BottomNavigationHelper.disableShiftMode(bottom_navigation)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val fm = supportFragmentManager
        fm.popBackStackImmediate()
    }
}
