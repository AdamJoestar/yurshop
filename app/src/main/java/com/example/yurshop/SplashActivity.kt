package com.example.yurshop
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Berpindah ke OnboardingActivity
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // Sesuaikan waktu delay dengan keinginan Anda
    }
}
