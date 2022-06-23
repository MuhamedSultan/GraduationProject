package com.example.we_care

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import chat.ChatUsers
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import fragment.*
import kotlinx.android.synthetic.main.activity_navigition_drawer.*

class NavigationDrawer : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private val mAuth : FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    var  fragment : Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigition_drawer)

        supportFragmentManager.beginTransaction().replace(R.id.fragment, Home()).commit()
        navigationView.setCheckedItem(R.id.Home_navi)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Navigation View"

        navigationView.setNavigationItemSelectedListener(this)


        val actionToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.drawe_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(actionToggle)
        actionToggle.syncState()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {



        var Itemid = item.getItemId()
        if (Itemid == R.id.Home_navi) {
            fragment = Home()
            supportActionBar?.title="Home"
        } else if (Itemid == R.id.events_navi) {
            fragment = Events()
        } else if (Itemid == R.id.lost_navi) {
            fragment = LostPeople()
        } else if (Itemid == R.id.Profile_navi) {
            val intent =Intent(this,Profile::class.java)
            startActivity(intent)
        } else if (Itemid == R.id.adoption_navi) {
            fragment = Adoption()
        } else if (Itemid == R.id.connect_navi) {
            fragment = ContactUs()
        } else if (Itemid == R.id.donation_navi) {
            fragment = DonationRecycler()
        } else if (Itemid == R.id.about_navi) {
            fragment = About()
        } else if (Itemid == R.id.recognition_navi) {
            fragment = RecognitionByCamera()
            supportActionBar?.title="recognition_navi"

        }else if (Itemid==R.id.chat){
            val intent =Intent(this,ChatUsers::class.java)
            startActivity(intent)
        }
        else  {
            mAuth.signOut()
                var intent = Intent(applicationContext,SignUp::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            }



        if (fragment != null) {
            var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, fragment!!)
            transaction.commit()
        }
//         else {
//            mAuth
//            var intent = Intent(applicationContext,SignUp::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            startActivity(intent)
//
//        }

    closeDrawer()

        return true
    }
    private fun closeDrawer () {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

        override fun onBackPressed () {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
               closeDrawer()
            else
                super.onBackPressed()
        }
}




