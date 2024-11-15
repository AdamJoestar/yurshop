package com.example.yurshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        val btnForgot = findViewById<Button>(R.id.btnForgot)
        btnForgot.setOnClickListener {
            // Berpindah ke tampilan ResetPasswordActivity
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        btnForgot.setOnClickListener {
            try {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()  // Menampilkan pesan error di logcat
            }
        }


    }
}
