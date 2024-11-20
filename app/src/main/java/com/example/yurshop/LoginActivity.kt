package com.example.yurshop

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi referensi database
        database = FirebaseDatabase.getInstance().getReference("users")

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<EditText>(R.id.etNewEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvForgotPassword = findViewById<TextView>(R.id.Forgot)
        val ivTogglePassword = findViewById(R.id.ivTogglePassword) as ImageView
        val tvRegister = findViewById<TextView>(R.id.BelumPunyaAkun)
        val registerSpannableString = SpannableString(tvRegister?.text)
        val daftarStartIndex = registerSpannableString.indexOf("Daftar")

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
            val forgotSpannableString = SpannableString(tvForgotPassword.text)
            val forgotClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // Pindah ke ForgotActivity saat diklik
                    val intent = Intent(this@LoginActivity, ForgotActivity::class.java)
                    startActivity(intent)
                }
            }
            forgotSpannableString.setSpan(
                forgotClickableSpan,
                0,
                forgotSpannableString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvForgotPassword.text = forgotSpannableString
            tvForgotPassword.movementMethod = LinkMovementMethod.getInstance()

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


            val registerClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // Pindah ke RegisterActivity saat diklik
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }

            val daftarEndIndex = daftarStartIndex + "Daftar".length
            registerSpannableString.setSpan(
                registerClickableSpan,
                daftarStartIndex,
                daftarEndIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvRegister.text = registerSpannableString
            tvRegister.movementMethod = LinkMovementMethod.getInstance()
        }}



    private fun loginUser(email: String, password: String) {
        database.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val dbPassword = userSnapshot.child("password").value.toString()
                        if (dbPassword == password) {
                            // Login berhasil, pindah ke MainActivity
                            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Password salah", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Email tidak terdaftar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Gagal menghubungi server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
