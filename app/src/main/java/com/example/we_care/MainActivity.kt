package com.example.we_care

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy{
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnlogin = findViewById<Button>(R.id.loginbtn)
        var signupbtn = findViewById<Button>(R.id.signupbtn)
        supportActionBar?.hide()
        btnlogin.setOnClickListener {
            var intentlogin = Intent(this, Login::class.java)
            startActivity(intentlogin)
        }
        signupbtn.setOnClickListener {
            var intentsignup = Intent(this, SignUp::class.java)
            startActivity(intentsignup)
        }
    }
    override fun onStart () {
        super.onStart()
        if (mAuth.currentUser?.uid != null) {
            val intentMainActivity =
                Intent( this,NavigationDrawer::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intentMainActivity)
        }
    }
}