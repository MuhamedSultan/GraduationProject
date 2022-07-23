package com.example.we_care

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import chat.ChatUsers
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import fragment.*
import kotlinx.android.synthetic.main.activity_navigition_drawer.*

class NavigationDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    var fragment: Fragment? = null
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

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("DataOfUser", MODE_PRIVATE)

        val userName = sharedPreferences.getString("userName", "Nothing")

        val headerLayout: View = navigationView.inflateHeaderView(R.layout.heder)
        val user_navigation = headerLayout.findViewById<TextView>(R.id.user_navigation)

        user_navigation.text = userName


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        val Itemid = item.getItemId()
        if (Itemid == R.id.Home_navi) {
            fragment = Home()
            supportActionBar?.title = "Home"
        } else if (Itemid == R.id.events_navi) {
            fragment = Events()
            supportActionBar?.title = "Event"
        } else if (Itemid == R.id.lost_navi) {
            fragment = LostPeople()
            supportActionBar?.title = "Lost People"
        } else if (Itemid == R.id.Profile_navi) {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            supportActionBar?.title = "Profile"

        } else if (Itemid == R.id.adoption_navi) {
            fragment = Adoption()
            supportActionBar?.title = "Adoption"

        } else if (Itemid == R.id.connect_navi) {
            fragment = ContactUs()
            supportActionBar?.title = "Contact Us"

        } else if (Itemid == R.id.donation_navi) {
            fragment = DonationRecycler()
            supportActionBar?.title = "Donation"

        } else if (Itemid == R.id.about_navi) {
            fragment = About()
            supportActionBar?.title = "About"

        } else if (Itemid == R.id.recognition_navi) {
            fragment = RecognitionByCamera()
            supportActionBar?.title = "recognition By Camera"

        } else if (Itemid == R.id.chat) {
            val intent = Intent(this, ChatUsers::class.java)
            startActivity(intent)
        } else {
           val sharedPreferences: SharedPreferences =
                getSharedPreferences("DataOfUser", MODE_PRIVATE)
           sharedPreferences.edit().clear().apply()

           // mAuth.signOut()
            val intent = Intent(this, Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }



        if (fragment != null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, fragment)
            transaction.commit()
        }
//
        closeDrawer()

        return true
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer()
        else
            super.onBackPressed()
    }
}




