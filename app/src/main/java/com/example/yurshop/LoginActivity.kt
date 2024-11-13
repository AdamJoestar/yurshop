package com.example.yurshop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.BelumPunyaAkun)

        btnLogin.setOnClickListener {
            // Setelah login berhasil, berpindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val etPassword = findViewById(R.id.etPassword) as EditText
        val ivTogglePassword = findViewById(R.id.ivTogglePassword) as ImageView
        ivTogglePassword.setOnClickListener {
            if (etPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.ice_eye_off)
            } else {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.ice_eye)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Membuat SpannableString untuk TextView tvRegister
        val spannableString = SpannableString(tvRegister.text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Pindah ke RegisterActivity saat diklik
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

        // Mencari index kata "Daftar" dan menerapkan ClickableSpan
        val daftarStartIndex = spannableString.indexOf("Daftar")
        val daftarEndIndex = daftarStartIndex + "Daftar".length
        spannableString.setSpan(clickableSpan, daftarStartIndex, daftarEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Mengatur SpannableString ke TextView dan mengaktifkan link
        tvRegister.text = spannableString
        tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }
}