package com.example.yurshop
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        val tvIntroTitle = findViewById<TextView>(R.id.tvIntroTitle)
        val text = "Selamat Datang di YurShop"
        val spannableString = SpannableString(text)
        val colorGreen = ContextCompat.getColor(this, R.color.green)
        spannableString.setSpan(
            ForegroundColorSpan(colorGreen),
            18,
            21,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvIntroTitle.text = spannableString

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
