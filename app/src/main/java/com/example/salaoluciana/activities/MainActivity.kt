package com.example.salaoluciana.activities

import BottomSheetFragment
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.salaoluciana.R
import com.example.salaoluciana.fragments.AccountUserFragment
import com.example.salaoluciana.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setUserName()
        openFragment(HomeFragment())
        navigationListner()

    }


    private fun setUserName() {

        var name : String? = FirebaseAuth.getInstance().currentUser!!.displayName

        name.let {
            bottom_navigation.menu.findItem(R.id.nav_user).title = it
        }


        name ?: kotlin.run {
            bottom_navigation.menu.findItem(R.id.nav_user).title = "Usuario"
        }


    }

    private fun navigationListner() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_test -> {
                    startActivity(Intent(this,TestActivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    openFragment(HomeFragment())
                    true
                }
                R.id.nav_user-> {

                    openFragment(AccountUserFragment())
                    true
                }
                else -> {
                    //openFragment(BottomSheetFragment.newInstance())
                    //startActivity(Intent(this,MainBottomSheetActivity::class.java))
                    startActivity(Intent(this,AddOrderActivity::class.java))
                    //startActivity(Intent(this,BottomSheetActivity::class.java))
                    //startActivity(Intent(this,Spinner::class.java))
                    true
                }
            }
        }
    }


    private fun openFragment(fragment: Fragment) {
        var fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(container.id,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}