package com.example.we_care

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
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
                var intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
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
    }
}
