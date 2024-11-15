package com.example.yurshop
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val appNameTextView = findViewById<TextView>(R.id.appNameTextView)
        val text = "Yurshop"
        val spannableString = SpannableString(text)
        val colorGreen = ContextCompat.getColor(this, R.color.green)
        spannableString.setSpan(
            ForegroundColorSpan(colorGreen),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        appNameTextView.text = spannableString

        Handler().postDelayed({
            // Berpindah ke OnboardingActivity
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // Sesuaikan waktu delay dengan keinginan Anda
    }
}
