package com.example.spark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class NewSplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_splash_screen)
        Handler().postDelayed({
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        },5000)
    }
}