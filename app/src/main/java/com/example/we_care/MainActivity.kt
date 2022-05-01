package com.example.we_care

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
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
}