package com.example.we_care

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.we_care.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding?=null
    private val mAuth: FirebaseAuth by lazy{
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

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
        continueAsGuest()
    }
    private fun continueAsGuest (){
        val text = "Login As Guest"
        val string = SpannableString(text)
        val span = UnderlineSpan()

        string.setSpan(span, 9, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val clickableSpan: ClickableSpan = object : android.text.style.ClickableSpan() {
            override fun onClick(view: View) {
                var intent = Intent(this@MainActivity, CameraActivity2::class.java)
                startActivity(intent)
            }
        }
        string.setSpan(clickableSpan, 9, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding?.guest?.text = string
        binding?.guest?.movementMethod = LinkMovementMethod.getInstance()

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