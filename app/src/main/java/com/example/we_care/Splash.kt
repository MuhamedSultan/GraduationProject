package com.example.we_care

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        val counter = intArrayOf(0)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar1)
        val handler = Handler()
        handler.postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                   val sharedPreferences: SharedPreferences =
                getSharedPreferences("DataOfUser", MODE_PRIVATE)
                val userName = sharedPreferences.getString("userName", null)

                if(userName==null){
                    val intent1 = Intent(this, MainActivity::class.java)
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent1)
                }else {
                    val intent2 = Intent(this, NavigationDrawer::class.java)
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent2)
                }
            },
            3500
        )
        progressBar.visibility = View.VISIBLE
        val t = Timer()
        val tt: TimerTask = object : TimerTask() {
            override fun run() {
                counter[0]++
                progressBar.progress = counter[0]
                if (counter[0] == 100) {
                    t.cancel()
                }
            }
        }
        t.schedule(tt, 0, 50)

//        val sharedPreferences: SharedPreferences =
//            getSharedPreferences("userData", MODE_PRIVATE)
//
//        val userName = sharedPreferences.getString("userName", "Nothing")

    }

}
